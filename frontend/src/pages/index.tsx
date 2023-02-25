import { Nav } from "@/components/Nav/Nav";
import styles from "./styles/login.module.scss";
import Head from "next/head";
import Link from "next/link";
import { useEffect, useState } from "react";
import Home from "./create";
import UserService from "@/services/UserService";
import { useRouter } from "next/router";

export default function Login() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useState("");
  const router = useRouter();

  useEffect(() => {
    if (sessionStorage.getItem("serviceToken") !== null) {
      router.push("/create");
    }
  }, [router]);

  useEffect(() => {
    if (token !== "") {
      sessionStorage.setItem("serviceToken", token);
    }
  }, [token]);

  const handleClick = () => {
    const user = {
      userName,
      password,
    };
    UserService.login(user).then((res) => {
      setToken(res);
      router.push("/");
    });
  };

  return (
    <>
      <Head>
        <title>slAIds</title>
        <meta name="viewport" content="initial-scale=1.0, width=device-width" />
      </Head>

      <div className={styles.cardContainer}>
        <div className={styles.card}>
          <div className={styles.formField}>
            <label htmlFor="usernameInput" className={styles.title}>
              Username
            </label>
            <input
              value={userName}
              type="text"
              id="usernameInput"
              className={styles.input}
              placeholder="user12"
              onChange={(e) => {
                setUserName(e.target.value);
              }}
            />
          </div>
          <div className={styles.formField}>
            <label htmlFor="passwordInput" className={styles.title}>
              Password
            </label>
            <input
              value={password}
              type="password"
              id="passwordInput"
              className={styles.input}
              placeholder="%der351"
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
          </div>
          <button className={styles.button} onClick={handleClick}>
            Log in
          </button>
          <div className={styles.linkWrapper}>
            <Link className={styles.link} href="/signup">
              Don&apos;t have an account?
            </Link>
          </div>
        </div>
      </div>
    </>
  );
}
