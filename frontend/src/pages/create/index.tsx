import { Carousel } from "@/components/Carousel/Carousel";
import { ResumeCard } from "@/components/CarouselCards/ResumeCard";
import { StyleCard } from "@/components/CarouselCards/StyleCard";
import { OptionsCard } from "@/components/CarouselCards/OptionsCard";
import { GenerateCard } from "@/components/CarouselCards/GenerateCard";

import { useEffect, useState } from "react";
import styles from "../styles/create.module.scss";
import NotesIcon from "@mui/icons-material/Notes";
import ColorLensIcon from "@mui/icons-material/ColorLens";
import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";
import DoneRoundedIcon from "@mui/icons-material/DoneRounded";
import PresentationFile from "@/entities/PresentationFile";
import { Preview } from "@/components/Preview/Preview";
import { AnimatePresence } from "framer-motion";
import PresentationRequest, {
  getDefaultPresentationRequest,
} from "@/entities/PresentationRequest";
import { useRouter } from "next/router";

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

export default function Home() {
  const [presentationRequest, setPresentationRequest] =
    useState<PresentationRequest>(getDefaultPresentationRequest());
  const [isPreviewOpen, setIsPreviewOpen] = useState(false);
  const [presentationFile, setPresentationFile] =
    useState<PresentationFile | null>(null);
  const router = useRouter();

  useEffect(() => {
    if (!sessionStorage.getItem("serviceToken")) {
      router.push("/");
    }
  }, [router]);

  return (
    <>
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
            presentationRequest={presentationRequest}
            setPresentationRequest={setPresentationRequest}
          />
          <StyleCard
            presentationRequest={presentationRequest}
            setPresentationRequest={setPresentationRequest}
          />
          <OptionsCard
            presentationRequest={presentationRequest}
            setPresentationRequest={setPresentationRequest}
          />
          <GenerateCard
            presentationRequest={presentationRequest}
            setPresentationRequest={setPresentationRequest}
            setPresentationFile={setPresentationFile}
            setIsPreviewOpen={setIsPreviewOpen}
          />
        </Carousel>
      </div>
    </>
  );
}
