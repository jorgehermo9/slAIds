import "../styles/globals.scss";
import "../styles/builders.scss";
import type { AppProps } from "next/app";
import { Nav } from "@/components/Nav/Nav";
import NotificationManager from "@/components/NotificationManager/NotificationManager";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <NotificationManager>
        <Nav />
        <Component {...pageProps} />
      </NotificationManager>
    </>
  );
}
