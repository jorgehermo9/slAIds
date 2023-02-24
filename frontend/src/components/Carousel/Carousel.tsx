import { Children, cloneElement, isValidElement, useState } from "react";
import styles from "./carousel.module.scss";
import ArrowFwdIcon from "@mui/icons-material/ArrowForwardIosRounded";
import ArrowBckIcon from "@mui/icons-material/ArrowBackIosRounded";
import SlideRequest, { getDefaultSlideRequest } from "@/entities/SlideRequest";

interface Props {
  labels: string[];
  children: React.ReactNode;
}

export const Carousel = ({ labels, children }: Props) => {
  const [index, setIndex] = useState(0);
  const [slideRequest, setSlideRequest] = useState<SlideRequest>(
    getDefaultSlideRequest()
  );

  const childrenWithProps =
    Children.map(children, (child) => {
      if (isValidElement(child)) {
        return cloneElement(child, { setSlideRequest });
      }
      return child;
    }) ?? [];

  const isFirst = () => index === 0;
  const isLast = () => index === childrenWithProps.length - 1;

  const nextCard = () => {
    if (!isLast()) {
      setIndex(index + 1);
    }
  };

  const prevCard = () => {
    if (!isFirst()) {
      setIndex(index - 1);
    }
  };

  return (
    <div className={styles.externalContainer}>
      <div className={styles.carouselContainer}>
        <div
          className={styles.arrowCircle}
          data-is-enabled={!isFirst()}
          onClick={() => prevCard()}
        >
          <ArrowBckIcon />
        </div>
        <div className={styles.carouselCardContainer}>
          {childrenWithProps[index]}
        </div>
        <div
          className={styles.arrowCircle}
          data-is-enabled={!isLast()}
          onClick={() => nextCard()}
        >
          <ArrowFwdIcon />
        </div>
      </div>
      <div className={styles.carouselDotsContainer}>
        {labels.map((label, i) => (
          <div
            key={i}
            data-is-active={i === index}
            className={styles.carouselDot}
            onClick={() => setIndex(i)}
          >
            <div className={styles.dotCircle}>
              <span className={styles.dotNumber}>{i}</span>
            </div>
            <span className={styles.dotLabel}>{label}</span>
          </div>
        ))}
      </div>
    </div>
  );
};
