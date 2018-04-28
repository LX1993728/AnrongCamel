#  Camel Artemis 处理大量消息(Large Message)

### 简介
 这个例子展示了如何在Apache Camel和ActiveMQ Artemis之间发送大量消息。
 当我们说大消息时，我们引用GB大小的消息。
 
 您应该能够在内存较低的JVM中运行Camel和Artemis，例如256 / 512mb等，并且仍然能够以GB之间的大小发送消息。
 
 这通过将大消息缓存到磁盘来工作。 Artemis将大量消息缓存到其数据/大消息目录，而Camel在路由期间使用流缓存来后台处理临时目录。
 
 ### Build
 
 通过maven来运行此示例，首先输入以下命令进行编译

     mvn compile
 
 ### 安装 ActiveMQ Artemis
 
 请下载并解压 Apache ActiveMQ Artemis 下载地址: <http://activemq.apache.org/artemis/download.html>
 
解压缩下载后，您可以创建一个名为`mybroker`的新代理： 

     $ cd apache-artemis-2.4.0 
     $ bin/artemis create mybroker
     
 ### 运行 ActiveMQ Artemis
 
 通过shell命令启动ActiveMQ:
 
     $ cd mybroker
     $ bin/artemis run
 
 在前台启动Artemis并保持运行，直到您点击<kbd>ctrl</kbd>+<kbd>c</kbd>关闭Artemis。
 
 ### 运行 Camel
 
 在运行此示例之前，请确保JVM通过执行已限制内存
 
     export MAVEN_OPTS="-Xmx256m"
 
 然后启动Camel应用程序
 
     mvn camel:run
 
 然后，您可以将文件复制到发送给Artemis的`target / inbox`文件夹，
 然后再返回到Camel并写入`target / outbox`文件夹。
 
 这适用于小文件和大文件，例如大小为GB的文件。 JVM也不会耗尽内存。
 
 停止示例点击<kbd>ctrl</kbd> + <kbd>c</kbd>。
 如果您重新启动它并继续输入数字，您应该会看到它记住了之前输入的值，因为它使用了持久存储。
 
 ### ActiveMQ Artemis web 控制台
 
 您可以浏览Artemis Web控制台：<http://localhost:8161/console>
 查看消费者和生产者数量等活动。您也可以从队列中删除所有消息，这是一项便捷的操作。
 
 
 ### Forum, Help, etc
 
 If you hit an problems please let us know on the Camel Forums
 	<http://camel.apache.org/discussion-forums.html>
 
 Please help us make Apache Camel better - we appreciate any feedback you may
 have.  Enjoy!
 
 
 The Camel riders!
