export { default } from "next-auth/middleware";

export const config = {
  matcher: ["/products/:path*", "/stats/:path*", "/users/:path*"],
};
