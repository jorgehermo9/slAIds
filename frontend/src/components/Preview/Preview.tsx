import PresentationFile from "@/entities/PresentationFile";
import styles from "./preview.module.scss";
import { useState } from "react";
import { pdfjs, Document, Page } from "react-pdf";

pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.min.js`;

interface Props {
  presentationFile: PresentationFile;
  onClose: () => void;
}

export const Preview = ({ presentationFile }: Props) => {
  const [pageNumber, setPageNumber] = useState(1);
  const [numPages, setNumPages] = useState(0);

  function onDocumentLoadSuccess({ numPages }: { numPages: number }) {
    console.log("Hola");
    setNumPages(numPages);
  }

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h1 className={styles.previewTitle}>Preview</h1>
        <div className={styles.viewContainer}>
          <Document
            file={"./presentation.pdf"}
            onLoadSuccess={onDocumentLoadSuccess}
          >
            <div className={styles.previewSlidesContainer}>
              {[0, 1, 2].map((i) => (
                <Page
                  key={`page_${i + 1}`}
                  pageNumber={i + 1}
                  width={500}
                  renderAnnotationLayer={false}
                  renderTextLayer={false}
                />
              ))}
            </div>
          </Document>
        </div>
      </div>
    </div>
  );
};
