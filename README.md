# OpenFeatureDemo

Feature Flag（功能旗標）是一種軟體開發技術，允許團隊動態地開啟或關閉軟體中的某些功能，而無需更改代碼並重新部署應用程序。

使得團隊能夠更加靈活地控制功能的發布，實現測試、灰度發布、A/B 測試等策略，從而降低新功能發布時的風險。

OpenFeature 是一個開源的 Feature Flag 平台，旨在為開發者提供一個標準化、供應商中立的功能旗標接口。

## Demo示例

### DemoCase1: Secret-Button
- URL: [http://localhost:8080/page/secret-button](http://localhost:8080/page/secret-button)
- 描述：在 prod 環境中，無需重啟或重新部署即可開啟或關閉新功能，讓使用者測試並隨時回撤。

### WIP
- URL: [http://localhost:8080/](http://localhost:8080/)
- 描述：...

## 此專案使用
- [OpenFeature](https://openfeature.dev/)：使能夠自由切換 Provider
- [Flagsmith](https://flagsmith.com)：主要 Feature Flag 供應商（Provider）
- [FeatBit](https://featbit.co)：次要 Feature Flag 供應商，展示遷移過程
- Spring Boot：整體框架
- Hibernate：ORM
- Thymeleaf：模板引擎

## Contributors
- [陳澔恩](https://github.com/hank1224)
- [陳萭鍒](https://github.com/110306041)