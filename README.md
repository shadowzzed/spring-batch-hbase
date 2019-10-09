# hbase分支
目前完成了大部分工作,还有一部分版本问题没有解决
<br>直接导致的结果是第66W+条数据报错
```java
org.apache.hadoop.hbase.regionserver.NoSuchColumnFamilyException: org.apache.hadoop.hbase.regionserver.NoSuchColumnFamilyException: Column family table does not exist in region hbase:meta,,1.1588230740 in table 'hbase:meta', {TABLE_ATTRIBUTES => {IS_META => 'true', coprocessor$1 => '|org.apache.hadoop.hbase.coprocessor.MultiRowMutationEndpoint|536870911|'}, {NAME => 'info', BLOOMFILTER => 'NONE', VERSIONS => '10', IN_MEMORY => 'true', KEEP_DELETED_CELLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCACHE => 'true', BLOCKSIZE => '8192', REPLICATION_SCOPE => '0'}
	at org.apache.hadoop.hbase.regionserver.HRegion.checkFamily(HRegion.java:7649)
	at org.apache.hadoop.hbase.regionserver.HRegion.get(HRegion.java:6727)
	at org.apache.hadoop.hbase.regionserver.RSRpcServices.get(RSRpcServices.java:2029)
	at org.apache.hadoop.hbase.protobuf.generated.ClientProtos$ClientService$2.callBlockingMethod(ClientProtos.java:33644)
	at org.apache.hadoop.hbase.ipc.RpcServer.call(RpcServer.java:2170)
	at org.apache.hadoop.hbase.ipc.CallRunner.run(CallRunner.java:109)
	at org.apache.hadoop.hbase.ipc.RpcExecutor.consumerLoop(RpcExecutor.java:133)
	at org.apache.hadoop.hbase.ipc.RpcExecutor$1.run(RpcExecutor.java:108)
	at java.lang.Thread.run(Thread.java:748)

	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) ~[na:1.8.0_211]
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62) ~[na:1.8.0_211]
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45) ~[na:1.8.0_211]
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423) ~[na:1.8.0_211]
	at org.apache.hadoop.hbase.ipc.RemoteWithExtrasException.instantiateException(RemoteWithExtrasException.java:100) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.RemoteWithExtrasException.unwrapRemoteException(RemoteWithExtrasException.java:90) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.protobuf.ProtobufUtil.makeIOExceptionOfException(ProtobufUtil.java:282) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.protobuf.ProtobufUtil.handleRemoteException(ProtobufUtil.java:269) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.RegionServerCallable.call(RegionServerCallable.java:129) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.RpcRetryingCallerImpl.callWithRetries(RpcRetryingCallerImpl.java:107) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.HTable.get(HTable.java:386) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.HTable.get(HTable.java:360) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.MetaTableAccessor.getTableState(MetaTableAccessor.java:1078) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.ConnectionImplementation.getTableState(ConnectionImplementation.java:1974) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.ConnectionImplementation.isTableDisabled(ConnectionImplementation.java:605) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.RegionServerCallable.prepare(RegionServerCallable.java:219) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.RpcRetryingCallerImpl.callWithRetries(RpcRetryingCallerImpl.java:105) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.client.HTable.put(HTable.java:542) ~[hbase-client-2.1.1.jar:2.1.1]
	at com.zed.springbatchdemo.hbase.HBaseClient.insertOrUpdate(HBaseClient.java:102) ~[classes/:na]
	at com.zed.springbatchdemo.hbase.HBaseClient.insertOrUpdate(HBaseClient.java:84) ~[classes/:na]
	at com.zed.springbatchdemo.job2.writer.Job2Writer2.write(Job2Writer2.java:64) ~[classes/:na]
	at org.springframework.batch.core.step.item.SimpleChunkProcessor.writeItems(SimpleChunkProcessor.java:188) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.item.SimpleChunkProcessor.doWrite(SimpleChunkProcessor.java:154) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.item.SimpleChunkProcessor.write(SimpleChunkProcessor.java:287) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.item.SimpleChunkProcessor.process(SimpleChunkProcessor.java:212) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.item.ChunkOrientedTasklet.execute(ChunkOrientedTasklet.java:75) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.tasklet.TaskletStep$ChunkTransactionCallback.doInTransaction(TaskletStep.java:407) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.tasklet.TaskletStep$ChunkTransactionCallback.doInTransaction(TaskletStep.java:331) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.transaction.support.TransactionTemplate.execute(TransactionTemplate.java:140) ~[spring-tx-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at org.springframework.batch.core.step.tasklet.TaskletStep$2.doInChunkContext(TaskletStep.java:273) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.scope.context.StepContextRepeatCallback.doInIteration(StepContextRepeatCallback.java:82) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.repeat.support.RepeatTemplate.getNextResult(RepeatTemplate.java:375) ~[spring-batch-infrastructure-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.repeat.support.RepeatTemplate.executeInternal(RepeatTemplate.java:215) ~[spring-batch-infrastructure-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.repeat.support.RepeatTemplate.iterate(RepeatTemplate.java:145) ~[spring-batch-infrastructure-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.tasklet.TaskletStep.doExecute(TaskletStep.java:258) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.step.AbstractStep.execute(AbstractStep.java:203) ~[spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.SimpleStepHandler.handleStep(SimpleStepHandler.java:148) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.flow.JobFlowExecutor.executeStep(JobFlowExecutor.java:68) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.flow.support.state.StepState.handle(StepState.java:67) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.flow.support.SimpleFlow.resume(SimpleFlow.java:169) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.flow.support.SimpleFlow.start(SimpleFlow.java:144) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.flow.FlowJob.doExecute(FlowJob.java:136) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.job.AbstractJob.execute(AbstractJob.java:313) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.batch.core.launch.support.SimpleJobLauncher$1.run(SimpleJobLauncher.java:144) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.core.task.SyncTaskExecutor.execute(SyncTaskExecutor.java:50) [spring-core-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at org.springframework.batch.core.launch.support.SimpleJobLauncher.run(SimpleJobLauncher.java:137) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_211]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_211]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_211]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_211]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:343) [spring-aop-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198) [spring-aop-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) [spring-aop-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration$PassthruAdvice.invoke(SimpleBatchConfiguration.java:127) [spring-batch-core-4.1.2.RELEASE.jar:4.1.2.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) [spring-aop-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212) [spring-aop-5.1.9.RELEASE.jar:5.1.9.RELEASE]
	at com.sun.proxy.$Proxy70.run(Unknown Source) [na:na]
	at org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner.execute(JobLauncherCommandLineRunner.java:207) [spring-boot-autoconfigure-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner.executeLocalJobs(JobLauncherCommandLineRunner.java:181) [spring-boot-autoconfigure-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner.launchJobFromProperties(JobLauncherCommandLineRunner.java:168) [spring-boot-autoconfigure-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner.run(JobLauncherCommandLineRunner.java:163) [spring-boot-autoconfigure-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:781) [spring-boot-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:765) [spring-boot-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:319) [spring-boot-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215) [spring-boot-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1204) [spring-boot-2.1.8.RELEASE.jar:2.1.8.RELEASE]
	at com.zed.springbatchdemo.SpringBatchDemoApplication.main(SpringBatchDemoApplication.java:12) [classes/:na]
Caused by: org.apache.hadoop.hbase.ipc.RemoteWithExtrasException: org.apache.hadoop.hbase.regionserver.NoSuchColumnFamilyException: Column family table does not exist in region hbase:meta,,1.1588230740 in table 'hbase:meta', {TABLE_ATTRIBUTES => {IS_META => 'true', coprocessor$1 => '|org.apache.hadoop.hbase.coprocessor.MultiRowMutationEndpoint|536870911|'}, {NAME => 'info', BLOOMFILTER => 'NONE', VERSIONS => '10', IN_MEMORY => 'true', KEEP_DELETED_CELLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCACHE => 'true', BLOCKSIZE => '8192', REPLICATION_SCOPE => '0'}
	at org.apache.hadoop.hbase.regionserver.HRegion.checkFamily(HRegion.java:7649)
	at org.apache.hadoop.hbase.regionserver.HRegion.get(HRegion.java:6727)
	at org.apache.hadoop.hbase.regionserver.RSRpcServices.get(RSRpcServices.java:2029)
	at org.apache.hadoop.hbase.protobuf.generated.ClientProtos$ClientService$2.callBlockingMethod(ClientProtos.java:33644)
	at org.apache.hadoop.hbase.ipc.RpcServer.call(RpcServer.java:2170)
	at org.apache.hadoop.hbase.ipc.CallRunner.run(CallRunner.java:109)
	at org.apache.hadoop.hbase.ipc.RpcExecutor.consumerLoop(RpcExecutor.java:133)
	at org.apache.hadoop.hbase.ipc.RpcExecutor$1.run(RpcExecutor.java:108)
	at java.lang.Thread.run(Thread.java:748)

<<<<<<< HEAD
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.onCallFinished(AbstractRpcClient.java:387) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.access$100(AbstractRpcClient.java:95) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient$3.run(AbstractRpcClient.java:410) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient$3.run(AbstractRpcClient.java:406) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.Call.callComplete(Call.java:103) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.Call.setException(Call.java:118) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.NettyRpcDuplexHandler.readResponse(NettyRpcDuplexHandler.java:162) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hadoop.hbase.ipc.NettyRpcDuplexHandler.channelRead(NettyRpcDuplexHandler.java:192) ~[hbase-client-2.1.1.jar:2.1.1]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:310) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:284) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.handler.timeout.IdleStateHandler.channelRead(IdleStateHandler.java:286) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1359) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:935) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:138) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:645) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:580) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:497) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:459) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858) ~[hbase-shaded-netty-2.1.0.jar:na]
	at org.apache.hbase.thirdparty.io.netty.util.concurrent.DefaultThreadFactory$DefaultRunnableDecorator.run(DefaultThreadFactory.java:138) ~[hbase-shaded-netty-2.1.0.jar:na]
	at java.lang.Thread.run(Thread.java:748) ~[na:1.8.0_211]
```
=======
# job2
* work
* 处理日志然后用正则表达式分析后逐条输出

# 分支说明
* master分支练习了如何去使用spring-batch
* mess-process 将job2的chunk重新构筑了一次，每次读取一个文本的全部信息存到数组中然后批处理
* final分支 完善job2，比mess-process分支更加成熟
>>>>>>> 532075056e7ff4bbfafa8b0c8c99ca6722f64225
