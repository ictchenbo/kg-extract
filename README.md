# kg-extract
基于结构化数据与抽取规则的知识抽取，从而构建知识图谱，数据源为MongoDB，提供RESTFul接口

详细设计可查看本人博客：https://blog.csdn.net/weixin_40338859/article/details/120729716


## Get Started
1. Install Jar Dependencis
```shell
maven install 
```

2. Maven Build
```shell
mvn build
```

3. Docker Build
```shell
docker build -t kg-extract:v1.0 .
```

4. Modify configuration file `src/resources/application.properties`
**IMPORTANT**: `spring.data.mongodb.uri` mongodb server uri, where source data were stored.

5. Run
```shell
docker run -itd -p 6215:6215 -v  src/resources/application.properties:/opt/application.properties kg-extract:v1.0 
```

6. Access 
```shell
curl -XPOST -H "Content-Type: application/json" "http://localhost:6215/kg-extract/v1.0/<app>/_map/table" -d '<RULE>'
```
其中<app>为区分不同应用，跟不用MongoDB数据库对应

<RULE>为定义的抽取规则。
