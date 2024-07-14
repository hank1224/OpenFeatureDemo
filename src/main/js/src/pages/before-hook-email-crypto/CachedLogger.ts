// CachedLogger.ts

import type { Logger } from "@openfeature/web-sdk";

interface LogDataDTO {
    message: string;
    flagKey: string;
    userEmailHashed: string | null;
    hookContext?: Record<string, any> | null;
    evaluationDetails?: Record<string, any> | null;
}

interface LogEntry {
    timestamp: Date;
    data: LogDataDTO[];
    logLevel?: string;
}

export class CachedLogger implements Logger {
    private errorLogs: LogEntry[] = [];
    private warnLogs: LogEntry[] = [];
    private infoLogs: LogEntry[] = [];
    private debugLogs: LogEntry[] = [];

    error(message: string, flagKey: string, userEmailHashed: string | null, hookContext: Record<string, any> ,evaluationDetails: Record<string, any> | null ): void {
        this.errorLogs.push({ timestamp: new Date(), data: [{ message, flagKey, userEmailHashed, hookContext, evaluationDetails}] });
    }

    warn(message: string, flagKey: string, userEmailHashed: string | null): void {
        this.warnLogs.push({ timestamp: new Date(), data: [{ message, flagKey, userEmailHashed }] });
    }

    info(message: string, flagKey: string, userEmailHashed: string | null): void {
        this.infoLogs.push({ timestamp: new Date(), data: [{ message, flagKey, userEmailHashed }] });
    }

    debug(message: string, flagKey: string, userEmailHashed: string | null, hookContext: Record<string, any>, evaluationDetails: Record<string, any> | null ): void {
        this.debugLogs.push({ timestamp: new Date(), data: [{ message, flagKey, userEmailHashed, hookContext,evaluationDetails}] });
    }

    async flushLog(): Promise<void> {
        const allLogs = [...this.errorLogs, ...this.warnLogs, ...this.infoLogs, ...this.debugLogs];
        allLogs.sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime());


        const jsonPayload = JSON.stringify(allLogs);
        console.log("Sending logs to server:", jsonPayload);  // 打印即将发送的 JSON 数据


        await fetch('http://localhost:8080/api/v1/logs/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(allLogs)
        });

        this.errorLogs = [];
        this.warnLogs = [];
        this.infoLogs = [];
        this.debugLogs = [];
    }

}
