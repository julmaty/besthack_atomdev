import type { Metadata } from "next";
import "../styles/reset.css";
import "../styles/global.css";
import React from 'react';
import Header from "@/components/Header/Header";


export const metadata: Metadata = {
    title: "Платформа продажи остатков топлива",
    description: "Реализовано в рамках хакатона Skoltech",
};

import AuthProvider from '@/utils/AuthProvider'
import {getServerSession} from "next-auth";
import AntDesignProvider from "@/utils/AntDesignProvider";
import ReactQueryProvider from "@/utils/ReactQueryProvider";

export default async function RootLayout({ children }: React.PropsWithChildren){

    const session = await getServerSession();
    console.log('session сервер', session);

    return (
        <html lang="ru">
            <body>
            <AuthProvider session={session}>
                <AntDesignProvider>
                    <ReactQueryProvider>
                            <Header />
                            {children}
                        </ReactQueryProvider>
                    </AntDesignProvider>
            </AuthProvider>
            </body>
        </html>
    );
}