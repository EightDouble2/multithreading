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
| `setPriority(int newPriority)` | 更改线程的优先级 | 
| `static void sleep(long millis)` | 在指定的毫秒内让当前正在执行的线程休眠 | 
| `void join()` | 等待该线程终止 | 
| `static void yield()` | 暂停当前正在执行的线程对象，并执行其他线程 | 
| `void interrupt()` | 中断线程 | 
| `boolean isAlive()` | 测试线程是否处于活动状态 |

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

## 读写锁(ReadWriteLock)

A ReadWriteLock维护一对关联的locks，一个用于只读操作，一个用于写入。read lock可以由多个阅读线程同时进行，write lock是独占的。

- 锁
  - 独占锁(写锁)：一次只能被一个线程占有。
  - 共享锁(读锁)：多个线程可以同时占有。
- 互斥
  - 读读不互斥
  - 读写互斥
  - 写写互斥
  
## 阻塞队列

### 队列

FIFO(先进先出)的数据结构。

- Queue(队列)
  - Deque(双端队列)
  - AbstractQueue(非阻塞队列)
    - PriorityQueue：由数组支持的有界队列。
    - ConcurrentLinkedQueue：由链接节点支持的可选有界队列。
  - BlockingQueue(阻塞队列)
    - ArrayBlockingQueue：由数组支持的有界队列。
    - LinkedBlockingQueue：由链接节点支持的可选有界队列。
    - PriorityBlockingQueue：一个由优先级堆支持的无界优先级队列。
    - DelayQueue：由优先级堆支持的、基于时间的调度队列。
    - SynchronousQueue：同步队列，利用BlockingQueue接口的简单聚集(rendezvous)机制。
    
### 四组API

| 方式 | 抛出异常 | 有返回值，不抛出异常 | 阻塞，等待 | 阻塞，等待超时 |
| --- | --- | --- | --- | --- |
| 添加 | `add()` | `offer()` | `put()` | `offer(E e, long timeout, TimeUnit unit)` |
| 移除 | `remove()` | `poll()` | `take()` | `poll(long timeout, TimeUnit unit)` |
| 检测队首元素 | `element()` | `peek()` | - | - |

### 同步队列(SynchronousQueue)

同步队列没有容量，放入一个元素之后必须等待取出之后，才能继续放入元素。

## 线程池

### 池化技术

池化技术能够减少资源对象的创建次数，提高程序的性能，特别是在高并发下这种提高更加明显。使用池化技术缓存的资源对象有如下共同特点：1、对象创建时间长；2、对象创建需要大量资源；3、对象创建后可被重复使用。

一个资源池具备如下功能：租用资源对象、归还资源对象、清除过期资源对象。

### 线程池方法

线程池不允许使用Executors创建，而是通过ThreadPoolExecutor的方式，更加明确线程池的运行规则，避免资源耗尽的风险。使用Executors返回线程池对象的弊端：
- `Executors.newSingleThreadExecutor()`：单个线程的线程池、`Executors.newFixedThreadPool(int nThreads)`：固定线程数的线程池。
  - 允许的请求队列长度为`Integer.MAX_VALUE`，可能会堆积大量的请求，从而导致OOM。
- `Executors.newCachedThreadPool()`：可伸缩大小的线程池。
  - 允许的创建线程数量为`Integer.MAX_VALUE`，可能会创建大量的线程，从而导致OOM。

### 线程池参数

Executors创建线程池的本质是使用ThreadPoolExecutor类。

```java
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, 
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        // 。。。
    }
```

- corePoolSize：核心线程池大小。
- maximumPoolSize：最大核心线程池大小。
- keepAliveTime：超时时间。
- unit：超时单位。
- workQueue：阻塞队列。
- threadFactory：线程工厂。
- handler：拒绝策略。

```java
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```

- `Executors.newSingleThreadExecutor()`：单个线程的线程池。
  - 核心线程池大小：1。
  - 最大核心线程池大小：1。
- `Executors.newFixedThreadPool(int nThreads)`：固定线程数的线程池。
  - 核心线程池大小：nThreads。
  - 最大核心线程池大小：nThreads。
- `Executors.newCachedThreadPool()`：可伸缩大小的线程池。
  - 核心线程池大小：0。
  - 最大核心线程池大小：`Integer.MAX_VALUE`。
  
### 线程池阻塞策略

当提交任务数大于corePoolSize的时候，会优先将任务放到workQueue阻塞队列中。当阻塞队列饱和后，会扩充线程池中线程数，直到达到maximumPoolSize最大线程数配置。此时，再多余的任务，则会触发线程池的拒绝策略了。

总结起来，也就是一句话，当提交的任务数大于(`workQueue.size()` + `maximumPoolSize`)，就会触发线程池的拒绝策略。

- `new ThreadPoolExecutor.AbortPolicy()`：抛出异常。
- `new ThreadPoolExecutor.CallerRunsPolicy()`：使用调用该线程的线程执行。
- `new ThreadPoolExecutor.DiscardPolicy()`：丢掉任务，不抛出异常。
- `new ThreadPoolExecutor.DiscardOldestPolicy()`：和最早的线程竞争，若失败则丢掉任务。

### 手动创建线程池

- IO密集型
  - 最大核心线程池大小应大于程序中十分消耗IO的线程数。
- CPU密集型
  - 最大核心线程池大小应等于CPU核数，保持CPU效率最高。
  - 获取CPU核数：`Runtime.getRuntime().availableProcessors()`。

```java
ExecutorService threadPool = new ThreadPoolExecutor(
    2,
    Runtime.getRuntime().availableProcessors(),
    3,
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<>(3),
    Executors.defaultThreadFactory(),
    new ThreadPoolExecutor.AbortPolicy());
```

## 函数式接口(Functional Interface)

一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。

接口使用`@FunctionalInterface`注解，JAVA8之前一般是用匿名类实现的，JAVA8之后可以被隐式转换为lambda表达式。

函数式接口有四大类型：
- Function(函数型接口)：有参数，有返回值。
- Predicate(断定型接口)：有参数，返回布尔值。
- Consumer(消费型接口)：只有参数，没有返回值。
- Supplier(供给型接口)：没有参数，只有返回值。

## 流式计算(Stream)

Java8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。Stream使用一种类似用SQL语句从数据库查询数据的直观方式来提供一种对Java集合运算和表达的高阶抽象。集合、数据库只应该用来存储，计算都应该交给流来操作。

## ForkJoin

ForkJoin是由Java7后提供多线并发处理框架。ForkJoin的框架的基本思想是分而治之。就是将一个复杂的计算，按照设定的阈值进行分解成多个计算，然后将各个计算结果进行汇总。相应的ForkJoin将复杂的计算当做一个任务。而分解的多个计算则是当做一个子任务。ForkJoin的最特别之处在于它还运用了一种叫work-stealing(工作窃取)的算法，这种算法的设计思路在于把分解出来的小任务放在多个双端队列中，而线程在队列的头和尾部都可获取任务。

## 异步回调

Future设计的初衷：对将来的某个事件的结果进行建模。

## Java内存模型(JMM)

java内存模型中规定了所有变量都存贮到主内存(如虚拟机物理内存中的一部分)中。每一个线程都有一个自己的工作内存(如cpu中的高速缓存)。线程中的工作内存保存了该线程使用到的变量的主内存的副本拷贝。线程对变量的所有操作(读取、赋值等)必须在该线程的工作内存中进行。不同线程之间无法直接访问对方工作内存中变量。线程间变量的值传递均需要通过主内存来完成。

JMM的一些同步的约定：
- 线程加锁前，必须读取主存中的最新值到工作内存中。
- 线程解锁前，必须把共享变量立刻刷回主存。
- 加锁和解锁是同一把锁。

内存交互操作有8种，虚拟机实现必须保证每一个操作都是原子的，不可在分的(对于double和long类型的变量来说，load、store、read和write操作在某些平台上允许例外)：
- lock (锁定)：作用于主内存的变量，把一个变量标识为线程独占状态。
- unlock (解锁)：作用于主内存的变量，它把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定。
- read (读取)：作用于主内存变量，它把一个变量的值从主内存传输到线程的工作内存中，以便随后的load动作使用。
- load (载入)：作用于工作内存的变量，它把read操作从主存中变量放入工作内存中。
- use (使用)：作用于工作内存中的变量，它把工作内存中的变量传输给执行引擎，每当虚拟机遇到一个需要使用到变量的值，就会使用到这个指令。
- assign (赋值)：作用于工作内存中的变量，它把一个从执行引擎中接受到的值放入工作内存的变量副本中。
- store (存储)：作用于主内存中的变量，它把一个从工作内存中一个变量的值传送到主内存中，以便后续的write使用。
- write (写入)：作用于主内存中的变量，它把store操作从工作内存中得到的变量的值放入主内存的变量中。

JMM对这八种指令的使用，制定了如下规则：
- 不允许read和load、store和write操作之一单独出现。即使用了read必须load，使用了store必须write。
- 不允许线程丢弃他最近的assign操作，即工作变量的数据改变了之后，必须告知主存。
- 不允许一个线程将没有assign的数据从工作内存同步回主内存。
- 一个新的变量必须在主内存中诞生，不允许工作内存直接使用一个未被初始化的变量。就是对变量实施use、store操作之前，必须经过assign和load操作。
- 一个变量同一时间只有一个线程能对其进行lock。多次lock后，必须执行相同次数的unlock才能解锁。
- 如果对一个变量进行lock操作，会清空所有工作内存中此变量的值，在执行引擎使用这个变量前，必须重新load或assign操作初始化变量的值。
- 如果一个变量没有被lock，就不能对其进行unlock操作。也不能unlock一个被其他线程锁住的变量。
- 对一个变量进行unlock操作之前，必须把此变量同步回主内存。

> 线程在运行时不知道内存的只是否已经修改过了。

Java 内存模型中的可见性、原子性和有序性。
- 可见性：
  - 可见性是一种复杂的属性，因为可见性中的错误总是会违背我们的直觉。通常，我们无法确保执行读操作的线程能适时地看到其他线程写入的值，有时甚至是根本不可能的事情。为了确保多个线程之间对内存写入操作的可见性，必须使用同步机制。
  - **可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的**。也就是一个线程修改的结果。另一个线程马上就能看到。比如：用volatile修饰的变量，就会具有可见性。volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存。所以对其他线程是可见的。但是这里需要注意一个问题，volatile只能让被他修饰内容具有可见性，但不能保证它具有原子性。比如 volatile int a = 0；之后有一个操作a++；这个变量a具有可见性，但是a++依然是一个非原子操作，也就是这个操作同样存在线程安全问题。
  - 在Java中volatile、synchronized和final实现可见性。
- 原子性：
　- 原子是世界上的最小单位，具有不可分割性。比如a=0；(a非long和double类型)这个操作是不可分割的，那么我们说这个操作时原子操作。再比如：a++；这个操作实际是a = a + 1；是可分割的，所以他不是一个原子操作。非原子操作都会存在线程安全问题，需要我们使用同步技术（sychronized）来让它变成一个原子操作。一个操作是原子操作，那么我们称它具有原子性。java的concurrent包下提供了一些原子类，我们可以通过阅读API来了解这些原子类的用法。比如：AtomicInteger、AtomicLong、AtomicReference等。
　- 在Java中synchronized和在lock、unlock中操作保证原子性。
- 有序性：
　- Java语言提供了volatile和synchronized两个关键字来保证线程之间操作的有序性，volatile是因为其本身包含“禁止指令重排序”的语义，synchronized是由“一个变量在同一个时刻只允许一条线程对其进行lock操作”这条规则获得的，此规则决定了持有同一个对象锁的两个同步块只能串行执行。

## Volatile

Java语言提供了一种稍弱的同步机制，即volatile变量，用来确保将变量的更新操作通知到其他线程。当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。

在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。

当对非volatile变量进行读写的时候，每个线程先从内存拷贝变量到CPU缓存中。如果计算机有多个CPU，每个线程可能在不同的CPU上被处理，这意味着每个线程可以拷贝到不同的CPU cache中。

而声明变量是volatile的，JVM保证了每次读变量都从内存中读，跳过CPU cache这一步。

volatile写时前后会生成内存屏障，使前后的指令禁止顺序交换。

volatile是**可以保持可见性，不能保证原子性，由于内存屏障，可以保证避免指令重排的现象产生**。

## 单例模式

- 饿汉式
  - 类加载就创建好对象，可能会浪费资源。 
- DCL懒汉式(双重锁)
  - 当需要的时候再创建对象。
  - 需要使用双重锁模式和volatile关键字，避免并发和指令重排带来错误。  
- 静态内部类
- (以上方法均可以通过反射破坏单例模式)
- 枚举
  - 枚举已经实现了单例模式。
  - 枚举没有空参数的构造方法`java.lang.NoSuchMethodException`
  - 枚举不允许使用反射创建`java.lang.IllegalArgumentException: Cannot reflectively create enum objects`

## CAS(CompareAndSet 比较并交换)

`public final boolean compareAndSet(int expect, int update)`：原子类方法，比较期待值，相等则更新。

- 优点：
  - compareAndSet调用Unsafe类直接对内存进行操作，保证原子性。
- 缺点：
  - Unsafe类使用自旋锁，循环会影响效率。
  - 一次只能保证一个共享变量的原子性。
  - ABA问题：CAS时，如果另一个线程修改V值假设原来是A，先修改成B，再修改回成A。当前线程的CAS操作无法分辨当前V值是否发生过变化。
    - ABA解决：引入原子引用，增加版本号，类似于乐观锁。
    
## 锁

### 公平锁、非公平锁

- 公平锁
  - 每个线程抢占锁的顺序为先后调用lock方法的顺序依次获取锁，不可插队。
- 非公平锁
  - 每个线程抢占锁的顺序不定，谁运气好，谁就获取到锁，和调用lock方法的先后顺序无关，可以插队。
  - 锁默认都是非公平锁。

### 可重入锁

一个线程如果获取了一个锁，其内部调用的其他锁也能获取到，而不出现死锁。

### 自旋锁

是指当一个线程在获取锁的时候，如果锁已经被其它线程获取，那么该线程将循环等待，然后不断的判断锁是否能够被成功获取，直到获取到锁才会退出循环。

> 一般用CAS可以实现。

### 死锁

死锁是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。

解决：
- 使用`jps -l`定位进程号。
- 使用`jstack 进程号`找到死锁问题。