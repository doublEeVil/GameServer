java -server -Xms256m -Xmx512m -javaagent:../serverLib/HotswapAgent.jar=WorldServer.jar -Djava.ext.dirs=../serverLib:../lib:.  com.game.ipd.IpdServer