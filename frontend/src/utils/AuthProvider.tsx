"use client";
import React from "react";
import { SessionProvider } from "next-auth/react";
import AuthGuard from "@/components/AuthGuard/AuthGuard";

const AuthProvider = ({ children }: any) => {
    return <SessionProvider>
                <AuthGuard>
                    {children}
                </AuthGuard>
    </SessionProvider>;
};

export default AuthProvider;