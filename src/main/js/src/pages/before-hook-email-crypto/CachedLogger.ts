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
    sequence?: number;
}

export class CachedLogger implements Logger {
    private errorLogs: LogEntry[] = [];
    private warnLogs: LogEntry[] = [];
    private infoLogs: LogEntry[] = [];
    private debugLogs: LogEntry[] = [];
    private sequenceNumber: number = 0;

    private log(logs: LogEntry[], message: string, flagKey: string, userEmailHashed: string | null, hookContext?: Record<string, any>, evaluationDetails?: Record<string, any> | null): void {
        logs.push({
            timestamp: new Date(),
            sequence: this.sequenceNumber++,
            data: [{ message, flagKey, userEmailHashed, hookContext, evaluationDetails }]
        });
    }


    error(message: string, flagKey: string, userEmailHashed: string | null, hookContext: Record<string, any>, evaluationDetails: Record<string, any> | null): void {
        this.log(this.errorLogs, message, flagKey, userEmailHashed, hookContext, evaluationDetails);
    }

    warn(message: string, flagKey: string, userEmailHashed: string | null): void {
        this.log(this.warnLogs, message, flagKey, userEmailHashed);
    }

    info(message: string, flagKey: string, userEmailHashed: string | null): void {
        this.log(this.infoLogs, message, flagKey, userEmailHashed);
    }

    debug(message: string, flagKey: string, userEmailHashed: string | null, hookContext: Record<string, any>, evaluationDetails: Record<string, any> | null): void {
        this.log(this.debugLogs, message, flagKey, userEmailHashed, hookContext, evaluationDetails);
    }

    async flushLog(): Promise<void> {
        const allLogs = [...this.errorLogs, ...this.warnLogs, ...this.infoLogs, ...this.debugLogs];
        allLogs.sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime() || a.sequence - b.sequence);


        const jsonPayload = JSON.stringify(allLogs);
        console.log("Sending logs to server:", jsonPayload);

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
        this.sequenceNumber = 0;
    }

}
