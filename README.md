# 多线程 multithreading

## 线程简介

### 程序、进程、线程

![img](https://github.com/EightDouble2/multithreading/blob/master/src/main/resources/img/001.png)

- 程序
  - 指令和数据的有序集合，其本身没有任何运行的含义，是一个静态的概念。
- 进程
  - 执行程序的一次执行过程，是一个动态的概念。是系统资源分配的单位。
- 线程
  - 通常在一个进程中可以有若干个线程，一个进程至少有一个线程，否则就没有存在的意义。线程是CPU调度和执行的单位。

> 很多多线程是模拟出来的，真正的多线程是指有多个CPU，即多核，如服务器。如果是模拟出来的多线程，即在一个CPU的情况下，在同一个时间点，CPU只能执行一个代码，因为CPU切换得很快，所以有同时执行的错觉。

### 概念

- 线程就是独立的执行路径。
- 在程序运行时，即使没有自己创建线程，后台也会有多个线程，如主线程，GC线程。
- `main()` 方法称之为主线程，为系统的入口，用于执行整个程序。
- 在一个进程中，如果开辟了多个线程，线程的运行由调度器安排调度，调度器是与操作系统紧密相关的，先后顺序是不能人为干预的。
- 对同一份资源操作时，会存在资源抢夺的的问题，需要加入并发控制。
- 线程会带来额外的开销，如CPU调度时间，并发控制开销。
- 每个线程在自己的工作内存交互，内存控制不当会造成数据不一致。 

> Java无法直接开启线程，而是通过C++实现的本地方法操作硬件。

```java
private native void start0();
```

## **线程实现**

### 继承Thread类

- 自定义线程类继承Thread类。
- 重写 `run()` 方法，编写线程执行体。
- 创建线程对象，调用 `start()` 方法启动线程。
- 不建议使用：OOP(封装、继承、多态)单继承局限性

### 实现Runnable接口

推荐使用实现Runnable接口，避免Java单继承局限性。

- 自定义线程类实现Runnable接口。
- 实现 `run()` 方法，编写线程执行体。
- 创建线程对象，传入实现类对象，调用 `start()` 方法启动线程。
- 建议使用：避免单继承局限性，方便同一个对象被多个线程使用。

### 实现Callable接口

- 实现Callable接口，需要返回值类型。
- 重写 `call()` 方法，需要抛出异常。
- 创建目标对象。
- 创建执行服务：`ExecutorService executorService = Executors.newFixedThreadPool(1);`
- 提交执行：`Future<Boolean> result1 = executorService.submit(t);`
- 获取结果：`boolean r1 = result1.get();`
- 关闭服务：`executorService.shutdownNow();`

### 静态代理

真实对象和代理对象都要实现同一个接口，代理对象要代理真实角色。

优点：
- 代理对象可以做很多真实对象做不了的事情。
- 真实对象专注做自己的事情

### Lambda表达式

为什么要使用Lambda表达式：
- 避免匿名内部类定义过多。
- 可以让代码看起来更简洁。
- 去掉没有意义的代码，只留下核心的逻辑。

函数式接口：
- 任何接口，如果只包含唯一一个抽象方法，那么它就是一个函数式接口。
- 对于函数式接口，我们可以通过Lambda表达式来创建该接口的对象。

## 线程状态

### 线程状态

![img](https://github.com/EightDouble2/multithreading/blob/master/src/main/resources/img/002.png)

- 创建
  - 线程对象一旦创建就进入到新生状态。
- 就绪
  - 当调用 `start()` 方法，线程立即进入到就绪状态，但不意味着立即调度执行。
- 阻塞
  - 当调用 `sleep()`， `wait()` 或同步锁定时，线程进入阻塞状态，就是代码不往下执行，阻塞事件解除后，重新进入到就绪状态，等待CPU调度执行。
- 运行
  - 进入运行状态，线程才真正执行线程体的代码块。
- 死亡
  - 线程中断或结束，一旦进入死亡状态，就不能再次启动。
  
### 线程方法

| 方法 | 说明 | 
| --- | --- | 
| setPriority(int newPriority) | 更改线程的优先级 | 
| static void sleep(long millis) | 在指定的毫秒内让当前正在执行的线程休眠 | 
| void join() | 等待该线程终止 | 
| static void yield() | 暂停当前正在执行的线程对象，并执行其他线程 | 
| void interrupt() | 中断线程 | 
| boolean isAlive() | 测试线程是否处于活动状态 |

### 守护线程

- 虚拟机必须确保用户线程执行完毕
- 虚拟机不用等待守护线程执行完毕 

## **线程同步**

### 线程同步

处理多线程问题时，多个线程访问同一个对象，并且某些线程还想修改这个对象，这时候我们就需要线程同步。线程同步其实就是一种等待机制，多个需要同时访问此对象的线程进入这个**对象的等待池**形成队列，等待前面线程使用完毕，下一个线程再使用。

由于同一进程的多个线程共享同一块存储空间，在带来方便的同时，也带来了访问冲突的问题，为了保证数据在方法中被访问时的正确性，在访问时加入**锁机制synchronized**，当一个线程过的对象的排它锁时，独占资源，其他线程必须等待，使用后释放锁即可。

- 一个线程持有锁会导致其他所有需要此锁的线程挂起。
- 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度时延，引起性能问题。
- 如果一个优先级高的线程等待一个优先级低的线程释放锁，会导致优先级倒置，引起性能问题。

### 同步方法 

- 同步方法：`public synchronized void method(int args) {}`
- 由于我们可以通过private关键字来保证数据对象只能被方法访问，所以我们只要针对方法提出一套机制，这套机制就是synchronized关键字，它包括synchronized方法和synchronized块。
- synchronized方法控制对"对象"的访问，每个对象对应一把锁，每个synchronized方法都必须获得调用该方法的对象的锁才能执行，否则线程会阻塞，方法一旦执行，就独占该锁，直到该方法返回才释放锁，后面被阻塞的线程才能获得这个锁，继续执行。
  - 若将一个大的方法申明为synchronized会影响效率。
  
### 同步块

- 同步块：`synchronized (Obj) {}`
- Obj称之为同步监视器
  - Obj可以是任何对象，但是推荐使用共享资源作为同步监视器。
  - 同步方法中无需指定同步监视器，因为同步方法的同步监视器就是this，就是当前对象本身，或者class。
- 同步监视器的执行过程
  - 第一个线程访问，锁定同步监视器，执行其中的代码。
  - 第二个线程访问，发现同步监视器被锁定，无法访问。
  - 第一个线程访问完毕，解锁同步监视器。
  - 第二个线程访问，发现同步监视器没有锁，然后锁定并访问。
  
### 死锁

多个线程各自占有一些共享资源，并且互相等待其他线程占有的资源才能运行，而导致两个或者多个线程都在等待对方释放戏院，都停止执行的情形。某一个同步块同时拥有**两个以上对象的锁**时，就可能会发生死锁的问题。

产生死锁的四个必要条件：
- 互斥条件：一个资源每次只能被一个进程使用。
- 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
- 不剥夺条件：进程已获得的资源，在未使用完之前，不能强行剥夺。
- 循环等待条件，若干进程之间形成一种头尾相接的循环等待资源关系。

### Lock(锁)

- 从JDK5开始，Java提供了更强大的线程同步机制--通过显式定义同步锁对象来实现同步。同步锁使用Lock对象充当。
- `java.util.concurrent.locks.Lock`接口是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独立访问，每次只能有一个线程对Lock对象加锁，线程开始访问共享资源之前应先获得Lock对象。
- ReentrantLock类实现了Lock，它拥有与synchronized相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是ReentrantLock，可以显式加锁、释放锁。

synchronized与Lock的对比
- Lock是显式锁(手动开启和关闭锁，别忘记关闭锁)synchronized是隐式锁，出了作用域自动释放。
- Lock只有代码块锁，synchronized有代码块锁和方法锁。
- 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性(提供更多的子类)。
- 优先使用顺序
  - Lock类
  - 同步代码块(已经进入了方法体，分配了相应资源)
  - 同步方法(在方法体之外)

## 线程通信问题

### 生产者和消费者问题

- 假如仓库中只能存放一件产品，生产者将生产出来的产品放入仓库，消费者将仓库中的产品取走。
- 如果仓库中没有产品，则生产者将产品放入仓库，否则停止生产并等待，直到仓库中的产品被消费者取走为止。
- 如果仓库中放有产品，则消费者可以将产品取走消费，否则停止消费并等待，直到仓库中再次放入产品为止。

这是一个线程同步问题，生产者和消费者共享同一个资源，并且生产者和消费者之间相互依赖，互为条件：
- 对于生产者，没有生产产品之前，要通知消费者等待，而产生了产品之后，又需要马上通知消费者消费。
- 对于消费者，在消费之后，要通知生产者已经消费，需要生产新的产品已供消费。
- 在生产者消费者问题中，仅有synchronized是不够的
  - synchronized可阻止并发更新同一个共享资源，实现了同步
  - synchronized不能用来实现不同线程之间的消息传递(通信)
  
Java提供了几个方法解决线程之间的通信问题

| 方法名 | 作用 |
| --- | --- |
| `wait()` | 表示线程一直等待，直到其他线程通知，与sleep不同，会释放锁 |
| `wait()` | 指定等待的毫秒数 |
| `notify()` | 唤醒一个处于等待状态的线程 |
| `notifyAll()` | 唤醒同一个对象上所有调用`wait()`方法的线程，优先级高的线程优先调度 |

以上均是Object类的方法，都只能在同步方法或者同步代码块中使用，否则会抛出异常`IllegalMonitorStateException`

### 解决方式(并发协作模型"生产者/消费者模式")

- 管程法
  - 生产者：负责产生数据的模块
  - 消费者：负责处理数据的模块
  - 缓冲区：消费者不能直接使用生产者的数据，他们之间有个缓冲区。**生产者将生产好的数据放入缓冲区，消费者从缓冲区拿出数据**。
- 信号灯法
  - 通过标志位，通知生产者和消费者进行相应操作。

## 线程池

### 背景
  
经常创建和销毁、使用量特别大的资源，比如并发情况下的线程，对性能影响很大。

### 思路
  
提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中。可以避免频繁创建和销毁，实现重复利用。类似生活中的公共交通工具。

### 好处

- 提高影响速度(减少了创建新线程的时间)。
- 降低资源消耗(重复利用线程池中线程，不需要每次都创建)。
- 便于线程管理
  - corePoolSiz：核心池的大小。
  - maximumPoolSize：最大线程数。
  - keepAliveTime：线程没有任务时最多保持多长时间后会终止。
  
### 使用

- JDK5提供了线程池相关API：ExecutorService和Executors。
- ExecutorService：真正的线程池接口。常见子类TheardPoolExecutor。
  - `void execute(Runnable command)`：执行任务/命令，没有返回值，一般用来执行Runnable。
  - `<T> Future<T> submit(Callable<T> task)`：执行任务，有返回值，一般用来执行Callable。
  - `void shutdown()`：关闭线程池。
- Executors：工具类、线程池的工厂类，用于创建并返回不同类型的线程池。

# 并发编程 concurrent

## 什么是JUC

JUC就是`java.util.concurrent`工具包的简称。这是一个处理线程的工具包，JDK 1.5开始出现的。
- `java.util.concurrent`
- `java.util.concurrent.atomic`
- `java.util.concurrent.locks`

普通线程Thread、Runnable没有返回值，效率相比于Callable相对较低。

## 进程与线程

### 概念

进程是一个程序的集合，一个进程往往可以包含多个线程。

Java无法真正开启线程，而是通过C++实现的本地方法操作硬件。
```java
private native void start0();
```

### 并行与并发

**并发**
- 单核CPU，多线程交替执行。

**并行**
- 多核CPU，多线程同时执行。
- 并发编程的本质就是充分利用CPU资源。

```java
// 获取cpu的核数
System.out.println(Runtime.getRuntime().availableProcessors());
```

### 线程状态

```java
public enum State {
    // 新生
    NEW,
    // 运行
    RUNNABLE,
    // 阻塞
    BLOCKED,
    // 等待
    WAITING,
    // 超时等待
    TIMED_WAITING,
    // 终止
    TERMINATED;
}
```

### wait与sleep

- 来自不同的类
  - wait来自Object类。
  - sleep来自Thread类。
- 锁释放
  - wait会释放锁。
  - sleep不会释放锁。
- 使用范围
  - wait只能在同步代码块中使用。
  - sleep可以在任何地方使用。
  
## Lock锁

### 传统Synchronized

```java
public synchronized void test() {
    // 业务代码
}
```

### Lock接口

```java
public void test() {
    // 创建锁
    Lock lock = new ReentrantLock();
    // 加锁
    lock.lock(); 
    try {
        // 业务代码
    }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // 解锁
        lock.unlock(); 
    }
}
```

### Synchronized和Lock区别

- Synchronized内置的Java关键字，Lock是一个Java类。
- Synchronized无法判断获取锁的状态，Lock可以判断是否获取到了锁。
- Synchronized会自动释放锁，lock必须要手动释放锁！如果不释放锁，死锁。
- Synchronized线程1(获得锁，阻塞)、线程2(一直等待)；Lock锁就不一定会等待下去。
- Synchronized可重入锁，不可以中断的，非公平；Lock，可重入锁，可以判断锁，非公平(可以自己设置)。
- Synchronized适合锁少量的代码同步问题，Lock适合锁大量的同步代码。

## 锁的对象

- 两个同步方法
  - 两个线程谁先拿到锁，就先执行。synchronized锁的对象是方法的调用者。
- 两个同步方其中一个休眠4秒
  - 两个线程谁先拿到锁，就先执行。synchronized锁的对象是方法的调用者。
- 增加一个普通方法
  - 普通方法不需要判断锁，线程触发了就执行。
- 两个对象两个同步方法
  - 两个线程谁先触发就先执行。synchronized锁的对象是方法的调用者，所以锁对两个对象互不影响。
- 两个静态同步方法
  - 两个线程谁先拿到锁，就先执行。synchronized锁的对象是整个Class。
- 两个对象两个静态同步方法
  - 两个线程谁先拿到锁，就先执行。synchronized锁的对象是整个Class。
- 一个普通同步方法一个静态同步方法
  - 两个线程谁先触发就先执行。synchronized锁的对象不同，所以互不影响。
- 两个对象两个静态同步方法
  - 两个线程谁先触发就先执行。synchronized锁的对象不同，所以互不影响。
  
---

- new、this加锁，锁的是对象实例。
- static、class加锁，锁的是整个类的Class。

## 集合类不安全

普通集合类在并发写入的时候会发生`java.util.ConcurrentModificationException`并发修改异常。

**List**
- `List<String> list = new Vector<>();`
- `List<String> list = Collections.synchronizedList(new ArrayList<>());`
- `List<String> list = new CopyOnWriteArrayList<>();`

**Set**
- `Set<String> set = Collections.synchronizedSet(new HashSet<>());`
- `Set<String> set = new CopyOnWriteArraySet<>();`

**Map**
- `Map<String, String> map = new ConcurrentHashMap<>();`

CopyOnWrite(COW)写入时复制，计算机程序设计领域的一种优化策略。多个线程调用的时候，读取的时候是固定的，写入的时候会覆盖。为了在写入的时候避免覆盖，造成数据问题，读写分离。

## Callable

Callable接口类似于Runnable，因为他们都是为其实例可能由另一个线程执行的类设计的。然而，Runnable不返回结果，也不能抛出异常。

Callable有返回值，可以抛出异常，调用方法也不同，而且具有缓存功能，会阻塞，结果可能需要等待。

> FutureTask实现了RunnableFuture接口，RunnableFuture继承了Runnable接口。而FutureTask有Runnable和Callable两个构造器。所以Callable可以通过FutureTask适配器被Thread调用。

## JUC常用辅助类

### CountDownLatch(减法计数器)

允许一个或多个线程等待直到其他线程中执行的一组操作完成的同步辅助。

CountDownLatch用给定的计数初始化。`await()`方法阻塞，直到由于`countDown()`方法的调用导致当前计数**达到零**或者**超时**，之后所有等待线程被释放，并且任何后续的await调用立即返回。这是一个一次性的现象--计数器无法重置。

### CyclicBarrier(加法计数器)

允许一组线程全部等待彼此达到共同障碍点的同步辅助。循环阻塞在涉及固定大小的线程方的程序中很有用，这些线程必须偶尔等待彼此。屏障被称为循环，因为它可以在等待的线程被释放之后重新使用。

### Semaphore(信号量，限流)

一个计数信号量。在概念上，信号量维持一组许可证。如果有必要，每个`acquire()`都会阻塞，直到许可证可用，然后才能使用它。每个`release()`添加许可证，潜在地释放阻塞获取方。但是，没有使用实际的许可证对象; Semaphore只保留可用数量的计数，并相应地执行。信号量通常用于限制线程数，而不是访问某些(物理或逻辑)资源。

