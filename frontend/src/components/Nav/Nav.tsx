import React, { useEffect, useState } from "react";
import AccountCircleRoundedIcon from "@mui/icons-material/AccountCircleRounded";
import styles from "./nav.module.scss";
import Link from "next/link";

export const Nav = () => {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    setToken(sessionStorage.getItem("serviceToken"));
  }, [token]);

  const handleClick = () => {
    sessionStorage.removeItem("serviceToken");
    setToken(null);
  };

  return (
    <nav className={styles.navbar}>
      <div>
        <AccountCircleRoundedIcon className={styles.user} />
      </div>
      <div className={styles.buttonsWrapper}>
        {!token && (
          <Link className={styles.buttonLogin} href="/">
            Login
          </Link>
        )}
        {!token && (
          <Link className={styles.buttonSignup} href="/signup">
            Sign Up
          </Link>
        )}
        {token && (
          <Link className={styles.buttonLogin} href="/" onClick={handleClick}>
            Log out
          </Link>
        )}
      </div>
    </nav>
  );
};
