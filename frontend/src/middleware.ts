import { NextRequest, NextResponse} from 'next/server';
import { getToken } from 'next-auth/jwt';
//import { NextRequestWithAuth } from 'next-auth/middleware';

/*
 * Match all request paths except for the ones starting with:
 * - api (API routes)
 * - _next/static (static files)
 * - _next/image (image optimization files)
 * - favicon.ico (favicon file)
 */
const pathsToExclude = /^(?!\/(api|_next\/static|favicon\.ico|manifest|icon|static)).*$/;

// set of public pages that needed to be excluded from middleware
const publicPagesSet = new Set<string>(['/error']);

export default async function middleware(req: NextRequest) {

    if (!pathsToExclude.test(req.nextUrl.pathname) || publicPagesSet.has(req.nextUrl.pathname)){
        return NextResponse.next();
    }

    console.log('middleware:');
    const token = await getToken({ req });
    console.log('token', token);
    return NextResponse.next();
}








