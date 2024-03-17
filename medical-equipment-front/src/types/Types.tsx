export interface User {
    email: string,
    id: number,
    lastName: string,
    name: string,
    role: string,
    token: string
}

export interface AuthContextType {
    user: User | null;
    login: (user: User) => void;
    logout: () => void;
}

export interface Complaint {
    id: number;
    text: string;
    timestamp: Date,
    companyName: string | null;
    companyAdminName: string | null;
    answer: string | null;
    userName: string | null;
}


  