// EmailHasher.ts
class EmailHasher {
    static async hashEmail(email: string): Promise<string> {
        try {
            const encoder = new TextEncoder();
            const data = encoder.encode(email);
            const hashBuffer = await crypto.subtle.digest('SHA-256', data);
            const hashArray = Array.from(new Uint8Array(hashBuffer));
            return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
        } catch (error) {
            console.error('Failed to hash email:', error);
            throw new Error('Failed to hash email due to an internal error.');
        }
    }
}

export default EmailHasher;
