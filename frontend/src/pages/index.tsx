import { Nav } from "@/components/Nav/Nav";
import styles from "./styles/login.module.scss";
import Head from "next/head";
import Link from "next/link";
import { useEffect, useState } from "react";
import Home from "./create";

export default function Login() {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    setToken(sessionStorage.getItem("serviceToken"));
  }, [token]);

  return (
    <>
      <Head>
        <title>slAIds</title>
        <meta name="viewport" content="initial-scale=1.0, width=device-width" />
      </Head>

      {token ? (
        <Home />
      ) : (
        <>
          <Nav />

          <div className={styles.cardContainer}>
            <div className={styles.card}>
              <div className={styles.formField}>
                <label htmlFor="usernameInput" className={styles.title}>
                  Username
                </label>
                <input
                  type="text"
                  id="usernameInput"
                  className={styles.input}
                  placeholder="user12"
                />
              </div>
              <div className={styles.formField}>
                <label htmlFor="passwordInput" className={styles.title}>
                  Password
                </label>
                <input
                  type="password"
                  id="passwordInput"
                  className={styles.input}
                  placeholder="%der351"
                />
              </div>
              <div className={styles.linkWrapper}>
                <Link className={styles.link} href="/signup">
                  Don&apos;t have an account?
                </Link>
              </div>
            </div>
          </div>
        </>
      )}
    </>
  );
}
