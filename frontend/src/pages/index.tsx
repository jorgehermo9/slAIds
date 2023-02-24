import { Carousel } from "@/components/Carousel/Carousel";
import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import { StyleCard } from "@/components/CarouselCards/StyleCard";
import SlideRequest, { getDefaultSlideRequest } from "@/entities/SlideRequest";
import { useState } from "react";
import styles from "./styles/home.module.scss";

export default function Home() {
  const [slideRequest, setSlideRequest] = useState<SlideRequest>(
    getDefaultSlideRequest()
  );

  return (
    <div className={styles.center}>
      <div className={styles.title}>
        <span className={styles.bold}>Create</span>
        <span className={styles.thin}>your</span>
        <span className={styles.bold}>slides</span>
      </div>
      <Carousel labels={["Resume", "Style"]}>
        <ResumeCard
          slideRequest={slideRequest}
          setSlideRequest={setSlideRequest}
        />
        <StyleCard
          slideRequest={slideRequest}
          setSlideRequest={setSlideRequest}
        />
      </Carousel>
    </div>
  );
}
