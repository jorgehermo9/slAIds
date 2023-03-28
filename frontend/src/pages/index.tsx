import styles from "./styles/login.module.scss";
import Head from "next/head";
import Link from "next/link";
import { FormEvent, useEffect, useState } from "react";
import UserService from "@/services/UserService";
import { useRouter } from "next/router";
import { Title } from "@/components/Title/Title";

export default function Login() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  useEffect(() => {
    if (sessionStorage.getItem("serviceToken")) {
      router.push("/create");
    }
  }, [router]);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const user = {
      userName,
      password,
    };
    UserService.login(user).then((res) => {
      sessionStorage.setItem("serviceToken", res);
      router.push("/create");
    });
  };

  return (
    <>
      <div className={styles.cardContainer}>
        <Title />

        <form className={styles.card} onSubmit={handleSubmit}>
          <h2 className={styles.formTitle}>Login</h2>

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
          <button type="submit" className={styles.button}>
            Log in
          </button>
          <div className={styles.linkWrapper}>
            <Link className={styles.link} href="/signup">
              Don&apos;t have an account?
            </Link>
          </div>
        </form>
      </div>
    </>
  );
}
