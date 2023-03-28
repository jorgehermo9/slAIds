import "../styles/globals.scss";
import "../styles/builders.scss";
import type { AppProps } from "next/app";
import { Nav } from "@/components/Nav/Nav";
import NotificationManager from "@/components/NotificationManager/NotificationManager";
import Head from "next/head";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <Head>
        <title>slAIds</title>
        <meta name="viewport" content="initial-scale=1.0, width=device-width" />
      </Head>

      <NotificationManager>
        <Nav />
        <Component {...pageProps} />
      </NotificationManager>
    </>
  );
}
