del antlr-generated\\*.tokens
del antlr-generated\\wresl\\*.java
del antlr-generated\\wresl\\*.class
rmdir antlr-generated\\wresl /s /q

del src\\test\\convertWresl\\*.class 
del src\\wresl\\*.tokens 

del bin\\test\\convertWresl\\*.class
del bin\\wresl\\*.class

rmdir bin\\test\\convertWresl /s /q
rmdir bin\\test /s /q
rmdir bin\\wresl /s /q

rmdir test-output /s /q

