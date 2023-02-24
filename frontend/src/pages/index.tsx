import { Carousel } from "@/components/Carousel/Carousel";
import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import SlideRequest, { getDefaultSlideRequest } from "@/entities/SlideRequest";
import { useState } from "react";
import styles from "./index.module.scss";

export default function Home() {
  const [slideRequest, setSlideRequest] = useState<SlideRequest>(
    getDefaultSlideRequest()
  );

  return (
    <div className={styles.center}>
      <Carousel labels={["Resume", "Resume"]}>
        <ResumeCard
          slideRequest={slideRequest}
          setSlideRequest={setSlideRequest}
        />
        <ResumeCard
          slideRequest={slideRequest}
          setSlideRequest={setSlideRequest}
        />
      </Carousel>
    </div>
  );
}
