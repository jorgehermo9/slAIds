import React, { useEffect, useState } from "react";
import styles from "./nav.module.scss";
import Link from "next/link";
import { useRouter } from "next/router";

export const Nav = () => {
  const [token, setToken] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    setToken(sessionStorage.getItem("serviceToken"));
  }, [router]);

  const handleClick = () => {
    sessionStorage.removeItem("serviceToken");
  };

  return (
    <nav className={styles.navbar}>
      <span className={styles.logo} onClick={() => router.push("/create")}>
        slAIds
      </span>

      <div className={styles.buttonsWrapper}>
        {token && (
          <>
            <Link className={styles.buttonLogin} href="/" onClick={handleClick}>
              Log out
            </Link>
            <Link className={styles.buttonLogin} href="/slides">
              My slides
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};
