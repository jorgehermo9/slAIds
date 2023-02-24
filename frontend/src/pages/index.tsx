import { Carousel } from "@/components/Carousel/Carousel";
import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import styles from "./index.module.scss";

export default function Home() {
  return (
    <div className={styles.center}>
      <Carousel labels={["Resume", "Resume"]}>
        <ResumeCard />
        <ResumeCard />
      </Carousel>
    </div>
  );
}
