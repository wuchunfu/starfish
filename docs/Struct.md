#Struct

##Environment
```
E       env
int    state(some state support for env developing)
```

##Class
```
E       env
long    serialVersionId
String  name
String  packagePath
```

##TAG
```
E       env
long    serialVersionId
etc.
```

##Attribute
```
E       env
long    serialVersionId
String  className
String  name
bool    array
String  defaultValue 
int     type (custom or jvm)
```

##ClassInstanceBridge
（one environment one key and one serialVersionId）
```
E       env
K       key
long    serialVersionId
```

##Instance
```
E       env
K       key
```

##Relation
```
E       env
K       fromkey
K       toKey
String  property
```

##Property
```
E       env
K       key
String  property
T       obj
```
