import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import styles from "./index.module.scss";

export default function Home() {
  return (
    <div className={styles.center}>
      <ResumeCard />
    </div>
  );
}
