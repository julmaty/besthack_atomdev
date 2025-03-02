import type { Metadata } from "next";
import "../styles/reset.css";
import "../styles/global.css";
import React from 'react';

export const metadata: Metadata = {
    title: "Админ панель платформы продажи остатков топлива",
    description: "Реализовано в рамках хакатона Skoltech",
};
import AntDesignProvider from "../utils/AntDesignProvider";


export default async function RootLayout({ children }: React.PropsWithChildren){

    return (
        <html lang="ru">
            <body>
                <AntDesignProvider>
                            {children}
                    </AntDesignProvider>
            </body>
        </html>
    );
}