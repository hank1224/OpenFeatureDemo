// index.ts
import { OpenFeature, ProviderEvents } from '@openfeature/web-sdk';
import { setupEventHandlers } from "./eventHandlers";
import { staticFbProvider } from "./staticFbProvider";

document.addEventListener('DOMContentLoaded', async function() {
    try{
        // 設置 Provider
        await staticFbProvider();
        // 增加事件處理器以監聽 Provider 的就緒事件
        OpenFeature.addHandler(ProviderEvents.Ready, (eventDetails) => {
            console.log(`Ready event from: ${eventDetails?.providerName}:`, eventDetails);

            // 確保在 Provider 完全就緒後調用 setupEventHandlers
            setupEventHandlers();
        })
    } catch (error) {
        console.error('Provider initialization failed:', error);
    }});