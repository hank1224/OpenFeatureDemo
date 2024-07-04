function gatherClientData() {
    const clientData = {
        browserInfo: navigator.userAgent,
        timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone,
        preferredLanguage: navigator.language,
        languages: navigator.languages,
        connectionInfo: navigator.connection ? {
            type: navigator.connection.type,
            effectiveType: navigator.connection.effectiveType,
            downlink: navigator.connection.downlink
        } : undefined,
        currentTime: new Date().toISOString()
    };

    // 檢查性能API的可用性
    if (window.performance) {
        clientData.performanceData = {
            navigationTiming: performance.getEntriesByType("navigation"),
            resourceTiming: performance.getEntriesByType("resource")
        };
    }

    return clientData;
}
