import { Carousel } from "@/components/Carousel/Carousel";
import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import { StyleCard } from "@/components/CarouselCards/StyleCard";
import SlideRequest, { getDefaultSlideRequest } from "@/entities/SlideRequest";
import { useState } from "react";
import styles from "./styles/home.module.scss";
import NotesIcon from "@mui/icons-material/Notes";
import ColorLensIcon from "@mui/icons-material/ColorLens";
import PresentationFile from "@/entities/PresentationFile";
import { Preview } from "@/components/Preview/Preview";

export default function Home() {
  const [slideRequest, setSlideRequest] = useState<SlideRequest>(
    getDefaultSlideRequest()
  );
  const [isPreviewOpen, setIsPreviewOpen] = useState(true);
  const [presentationFile, setPresentationFile] =
    useState<PresentationFile | null>(new Blob());

  const properties = [
    {
      label: "Resume",
      icon: <NotesIcon />,
    },
    {
      label: "Style",
      icon: <ColorLensIcon />,
    },
  ];

  return (
    <>
      {isPreviewOpen && presentationFile && (
        <Preview
          presentationFile={presentationFile}
          onClose={() => setIsPreviewOpen(false)}
        />
      )}
      <div className={styles.externalContainer}>
        <div className={styles.title}>
          <span className={styles.bold}>Create</span>
          <span className={styles.thin}>your</span>
          <span className={styles.bold}>slides</span>
        </div>
        <Carousel properties={properties}>
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
    </>
  );
}
