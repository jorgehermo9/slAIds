/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: `http://${process.env.API_HOST}:8080/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
