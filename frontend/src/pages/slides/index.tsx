import { PresentationList } from "@/components/PresentationList/PresentationList";
import styles from "../styles/slides.module.scss";

export default function Slides() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.cardContainer}>
        <PresentationList />
      </div>
    </div>
  );
}
