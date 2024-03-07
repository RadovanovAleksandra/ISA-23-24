import React, { createContext, useState, useEffect, ReactNode } from 'react';
import { AuthContextType, User } from '../types/Types';

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const token = localStorage.getItem('token')!;

    if (token) {
        const id = +localStorage.getItem('id')!;
        const name = localStorage.getItem('name')!;
        const lastName = localStorage.getItem('lastName')!;
        const role = localStorage.getItem('role')!;
        const email = localStorage.getItem('email')!;
        
        const user: User = {
            email,
            id,
            lastName,
            name,
            role,
            token
        }

        setUser(user);
    }
  }, []);

  const login = (user: User) => {
    localStorage.setItem('token', user.token);
    localStorage.setItem('id', user.id.toString());
    localStorage.setItem('lastName', user.lastName);
    localStorage.setItem('name', user.name);
    localStorage.setItem('role', user.role);
    setUser(user);
  };

  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
