import { Carousel } from "@/components/Carousel/Carousel";
import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import { StyleCard } from "@/components/CarouselCards/StyleCard";
import { OptionsCard } from "@/components/CarouselCards/OptionsCard";
import { GenerateCard } from "@/components/CarouselCards/GenerateCard";

import SlideRequest, { getDefaultSlideRequest } from "@/entities/SlideRequest";
import { useState } from "react";
import styles from "./styles/home.module.scss";
import NotesIcon from "@mui/icons-material/Notes";
import ColorLensIcon from "@mui/icons-material/ColorLens";
import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";
import DoneRoundedIcon from "@mui/icons-material/DoneRounded";
import PresentationFile from "@/entities/PresentationFile";
import { Preview } from "@/components/Preview/Preview";
import { AnimatePresence } from "framer-motion";
import NotificationManager from "@/components/NotificationManager/NotificationManager";
import { Nav } from "@/components/Nav/Nav";

export default function Home() {
  const [slideRequest, setSlideRequest] = useState<SlideRequest>(
    getDefaultSlideRequest()
  );
  const [isPreviewOpen, setIsPreviewOpen] = useState(false);
  const [presentationFile, setPresentationFile] =
    useState<PresentationFile | null>(new Blob());

  const properties = [
    {
      label: "Prompt",
      icon: <NotesIcon />,
    },
    {
      label: "Style",
      icon: <ColorLensIcon />,
    },
    {
      label: "Options",
      icon: <SettingsRoundedIcon />,
    },
    {
      label: "Generate",
      icon: <DoneRoundedIcon />,
    },
  ];

  return (
    <>
      <Nav />

      <NotificationManager>
        <AnimatePresence>
          {isPreviewOpen && presentationFile && (
            <Preview
              presentationFile={presentationFile}
              onClose={() => setIsPreviewOpen(false)}
            />
          )}
        </AnimatePresence>
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
            <OptionsCard
              slideRequest={slideRequest}
              setSlideRequest={setSlideRequest}
            />
            <GenerateCard
              slideRequest={slideRequest}
              setSlideRequest={setSlideRequest}
              setPresentationFile={setPresentationFile}
              setIsPreviewOpen={setIsPreviewOpen}
            />
          </Carousel>
        </div>
      </NotificationManager>
    </>
  );
}
