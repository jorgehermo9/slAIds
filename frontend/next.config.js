/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  async rewrites() {
    return [
        {
            // Rewrite all requests to the API to the API host, and pass the path and
            // the URL parameters to the API.
            source: "/api/:path*",
            destination: `http://${process.env.API_HOST}:8080/:path*`,
          },
    //   {
    //     // Rewrite all requests to the API to the API host, and pass the path and
    //     // the URL parameters to the API.
    //     source: "/api/:path*",
    //     has: [
    //         {
    //             type: 'query',
    //             key: 'token',
    //             value: '(?<token>.*)' // Named capture group to match anything on the value
    //         }
    //     ],
    //     destination: `http://${process.env.API_HOST}:8080/:path*?token=:token`,
    //   },
    ];
  },
};

module.exports = nextConfig;
