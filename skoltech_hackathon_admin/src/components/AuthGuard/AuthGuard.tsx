"use client"
import {useEffect} from "react";
import {useSession} from "next-auth/react";
import ConditionalRender from "@/components/Universal/ConditionalRender/ConditionalRender";
import {usePathname, useRouter} from 'next/navigation'

export default function AuthGuard({children}) {
    const { data: session } = useSession();

    const router=useRouter();

    const pathname = usePathname();

    useEffect(()=>{

        if(session!==undefined) {

            const isAuthenticated=session!==null;

            console.log('isAuthenticated', isAuthenticated);

            if (isAuthenticated) {

                if (pathname == '/') {
                    return router.replace('/lots-list');
                }

                // redirects user from '/auth-pages/login' if authenticated
                if (pathname.startsWith('/auth-pages/login')) {
                    return router.replace('/lots-list');
                }

                // redirects user from '/auth-pages/register' if authenticated
                if (pathname.startsWith('/auth-pages/register')) {
                    return router.replace('/lots-list');
                }

            }else{
                if (pathname == '/') {
                    return router.replace('/auth-pages/login');
                }

                if (!pathname.startsWith('/auth-pages/register') && !pathname.startsWith('/auth-pages/login')) {
                    return router.replace('/auth-pages/login');
                }
            }
        }

    },[router, pathname, session]);

    return (
        <ConditionalRender condition={session!==undefined}>{children}</ConditionalRender>
    )
}