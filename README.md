# OpenFeatureDemo

Feature Flag（功能旗標）是一種軟體開發技術，允許團隊動態地開啟或關閉軟體中的某些功能，而無需更改代碼並重新部署應用程序。

使得團隊能夠更加靈活地控制功能的發布，實現測試、灰度發布、A/B 測試等策略，從而降低新功能發布時的風險。

OpenFeature 是一個開源的 Feature Flag 平台，旨在為開發者提供一個標準化、供應商中立的功能旗標接口。

## 我寫的說明文件

寫很久看一下啦拜託，幫你快速上手。

https://hank20011224.notion.site/OpenFeature-30c10c41092d42d0b0125acbdcf1c69d?pvs=73

## Demo示例

### Case1: Secret-Button
- URL：[http://localhost:8080/page/secret-button](http://localhost:8080/page/secret-button)
- 描述：在 prod 環境中，無需重啟或重新部署即可開啟或關閉新功能，讓使用者測試並隨時回撤。

### Case2: Multi-Button
- URL：[http://localhost:8080/page/multi-button](http://localhost:8080/page/multi-button)
- 描述：可以同時使用多個 Feature Flag 供應商，並且可以隨時切換，擺脫依賴。

### Case3: DynamicApiSwitcher

較難展示，僅提供 Swagger 介面做實測。

使用AOP實現：透過切面來決定使用新舊Service，為一整個Service層的切換，非單一function。

- URL：[SwaggerPage](http://localhost:8080/swagger-ui/index.html)
- 描述：模擬API版本切換，可以實施 A/B 測試、灰度發布等策略。
- 具體細節：
    - GET method
      - 新：套用 Redis 緩存查詢
      - 舊：直接進行 H2 查詢
    - POST method
      - 新舊無異，皆檢查唯一性後存入 H2

### Case4: OpenFeature Client-side
- URL：[http://localhost:8080/page/openfeature-client-side](http://localhost:8080/page/openfeature-client-side)
- 描述： 展示如何使用 JS 操作Client-side，並展示與Server-side之差異。

### Case5: Before-Hook with user-email Crypto
- URL：[http://localhost:8080/page/before-hook-email-crypto](http://localhost:8080/page/before-hook-email-crypto)
- 描述： 使用 Before Hook 來加解密用戶Email，防止傳遞EvalContent時洩漏隱私。
- 實作包含：Server/Client端、四種Hook、Error模擬、Logging。

## Deployment

要去註冊 Flagsmith 帳號，記得用備用信箱，Sales會一直寄廣告超煩，也可以考慮直接用我的。

### 啟動以下容器服務

- [docker-envs](/docker-envs/docker-compose.yml):
  - Redis: 作為 Case3 的緩存服務

- FeatBit Localhost:
  - 下載啟動：[FeatBit GitHub](https://github.com/featbit/featbit)
  - [http://localhost:8081](http://localhost:8081)，用 0.0.0.0:8081 會開不了
  - 官方有給[預設帳號密碼](https://github.com/featbit/featbit?tab=readme-ov-file#1-start-featbit)
  - Mac 會遇到 port 被佔用問題：[見此解決](https://blog.csdn.net/zhang35/article/details/123895204)

### 使用 Yarn2 安裝 JS 套件

WIP...

### 去服務商設定 Flags

注意！FeatBit 會 Case Sensitivity，統一皆使用 Kebab Case，如：`featbit-button`。

在個別服務中創建以下 Flag：
#### [Flagsmith Setting](https://app.flagsmith.com)

  - **Case1 Flag**
    - KeyID: `secret-button`
    - Type: Boolean
    
  - **Case2 Flag**
    - KeyID: `flagsmith-button`
    - Type: Boolean

#### [FeatBit Setting](http://localhost:8081)

  - **Case2 Flag**
    - KeyID: `featbit-button`
    - Type: Boolean
    
  - **Case3 Flag**
    - KeyID: `dynamic-api-switcher`
    - Type: Boolean
  
  - **Case4 Flag**
    - KeyID: `openfeature-client-side`
    - Type: String
      - 設定新功能與舊功能字串即可
  
  - **Case5 Flag**
    - KeyID: `before-hook-email-crypto`
    - Type: Boolean
      - 設定為true

### 建立 application.properties
創建`application.properties`，可以參考`sample-application.properties`

把 Flagsmith 和 FeatBit 的 API Key 填入

**Redis Port 設定為 6380**，因為 6379 是 FeatBit 的 Redis。

## 此專案使用
- [OpenFeature](https://openfeature.dev/)：使能夠自由切換 Provider
- [Flagsmith](https://flagsmith.com)：主要 Feature Flag 供應商（Provider）
- [FeatBit](https://featbit.co)：次要 Feature Flag 供應商


- Spring Boot：OpenFeature Server-side
- Hibernate：使用 JPA
- AOP：用切面實現API版本控制


- Spring Doc：建立 Swagger API 文件
- Thymeleaf：模板引擎


- JavaScript：OpenFeature Client-side
- Yarn2：管理 JS 套件
- webpack：打包 JS 檔案

### Goods interface
- [GET](http://localhost:8080/page/get-goods)
- [POST](http://localhost:8080/page/post-goods)

## Contributors
- [陳澔恩](https://github.com/hank1224)
- [陳萭鍒](https://github.com/110306041)