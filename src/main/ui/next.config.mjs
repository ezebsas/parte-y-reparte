/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
      loader: 'default', 

      // Despues cambiar
      remotePatterns: [
        {
          protocol: 'https',
          hostname: '**',
        },
        {
          protocol: 'http',
          hostname: '**',
        },
      ],
  },
};



export default nextConfig;
