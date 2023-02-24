import PresentationFile from "@/entities/PresentationFile";
import styles from "./preview.module.scss";
import { useEffect, useState } from "react";
import { pdfjs, Document, Page } from "react-pdf";
import CloseIcon from "@mui/icons-material/Close";
import { motion } from "framer-motion";

pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.min.js`;

interface Props {
  presentationFile: PresentationFile;
  onClose: () => void;
}

export const Preview = ({ presentationFile, onClose }: Props) => {
  const [pageNumber, setPageNumber] = useState(1);
  const [numPages, setNumPages] = useState(0);

  function onDocumentLoadSuccess({ numPages }: { numPages: number }) {
    console.log("Hola");
    setNumPages(numPages);
  }

  return (
    <motion.div
      transition={{ duration: 0.3 }}
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className={styles.overlay}
    >
      <motion.div
        transition={{ duration: 0.3 }}
        initial={{ scale: 0 }}
        animate={{ scale: 1 }}
        exit={{ scale: 0 }}
        className={styles.modal}
      >
        <button className={styles.closeButton} onClick={() => onClose()}>
          <CloseIcon />
        </button>
        <h1 className={styles.previewTitle}>Preview</h1>
        <Document
          className={styles.viewContainer}
          file={"./presentation.pdf"}
          onLoadSuccess={onDocumentLoadSuccess}
        >
          <div className={styles.previewSlidesContainer}>
            {Array.from(Array(numPages).keys()).map((i) => (
              <div
                data-is-selected={pageNumber === i + 1}
                key={`page_${i + 1}`}
                onClick={() => setPageNumber(i + 1)}
                className={styles.smallPreviewSlide}
              >
                <Page
                  pageNumber={i + 1}
                  width={300}
                  renderAnnotationLayer={false}
                  renderTextLayer={false}
                />
              </div>
            ))}
          </div>
          <div className={styles.bigPreviewContainer}>
            <Page
              className={styles.bigPreviewSlide}
              pageNumber={pageNumber}
              width={1200}
              renderAnnotationLayer={false}
              renderTextLayer={false}
            />
          </div>
        </Document>
      </motion.div>
    </motion.div>
  );
};
