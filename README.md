# OpenFeatureDemo

Feature Flag（功能旗標）是一種軟體開發技術，允許團隊動態地開啟或關閉軟體中的某些功能，而無需更改代碼並重新部署應用程序。

使得團隊能夠更加靈活地控制功能的發布，實現測試、灰度發布、A/B 測試等策略，從而降低新功能發布時的風險。

OpenFeature 是一個開源的 Feature Flag 平台，旨在為開發者提供一個標準化、供應商中立的功能旗標接口。

## 我寫的說明文件

寫很久看一下啦拜託，幫你快速上手。

https://hank20011224.notion.site/OpenFeature-2295e8c5e77e4b31b27f721b309b59a7?pvs=4

## Demo示例

### Case1: Secret-Button
- URL：[http://localhost:8080/page/secret-button](http://localhost:8080/page/secret-button)
- 描述：在 prod 環境中，無需重啟或重新部署即可開啟或關閉新功能，讓使用者測試並隨時回撤。

### Case2: Multi-Button
- URL：[http://localhost:8080/page/multi-button](http://localhost:8080/page/multi-button)
- 描述：可以同時使用多個 Feature Flag 供應商，並且可以隨時切換，擺脫依賴。

### Case3: API version control
- URL：[http://localhost:8080/page/](http://localhost:8080/page/)
- 描述：在 直接DB查詢、Redis緩存查詢 兩個API版本之間做靈活切換，可以實施 A/B 測試、灰度發布等策略。
- 具體細節：
  - 使用AOP實現：透過切面來決定使用新舊Service，為一整個Service層的切換，非單一function。
  - GET method
    - 新：套用 Redis 緩存查詢
    - 舊：直接進行 H2 查詢
  - POST method
    - 新舊無異，皆檢查唯一性後存入 H2

## 此專案使用
- [OpenFeature](https://openfeature.dev/)：使能夠自由切換 Provider
- [Flagsmith](https://flagsmith.com)：主要 Feature Flag 供應商（Provider）
- [FeatBit](https://featbit.co)：次要 Feature Flag 供應商


- Spring Boot：整體框架
- Hibernate：使用 JPA
- AOP：用切面實現API版本控制


- Spring Doc：建立 Swagger API 文件
- Thymeleaf：模板引擎

## Others
[SwaggerPage](http://localhost:8080/swagger-ui/index.html)

### Goods interface
- [GET](http://localhost:8080/page/get-goods)
- [POST](http://localhost:8080/page/post-goods)

## Contributors
- [陳澔恩](https://github.com/hank1224)
- [陳萭鍒](https://github.com/110306041)