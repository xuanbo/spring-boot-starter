# codegen

> 代码生成器，基于 jetbrick 模板引擎

## 功能

- 可视化代码生成器服务，适配 admin-spring-boot-starter 模块约定
- 内嵌 sqlite ，连接外部数据库（MySQL）进行代码生成
- 自定义模板

## 快速使用

- 启动 tk.fishfish.codegen.CodegenApplication 运行
- 或打包 mvn clean package 后运行 jar 包

## 自定义模板

程序启动后同级目录 templates 目录下为系统默认模板，可以增加自定义模板，比如 xxx.jetx 模板文件

然后在配置文件下追加模板渲染路径：

```yaml
fish:
  codegen:
    author: 迷途小码农
    templates:
      # 模板 -> 生成 java 代码路径
      # ${pkg} 包名转化后的路径
      # ${entity} 实体名称
      # ${separator} 文件分割符
      entity: ${pkg}${separator}entity${separator}${entity}.java
      condition: ${pkg}${separator}condition${separator}${entity}Condition.java
      repository: ${pkg}${separator}repository${separator}${entity}Repository.java
      service: ${pkg}${separator}service${separator}${entity}Service.java
      serviceImpl: ${pkg}${separator}service${separator}impl${separator}${entity}ServiceImpl.java
      controller: ${pkg}${separator}controller${separator}${entity}Controller.java
      # 自定义模板文件
      xxx: ${pkg}${separator}xxx{separator}${entity}XXX.java
```

最后重启程序。
