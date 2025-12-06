package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * User-8, Goal-2: ReviewCustomerFeedback
 *
 * Attributes:
 *  - officerID: int
 *  - feedbackID: int
 *
 * Methods:
 *  + viewAllFeedback(): List<Feedback>
 *  + viewFeedbackDetails(feedbackID): Feedback
 *  + markAsReviewed(feedbackID, notes): boolean
 */
public class ReviewCustomerFeedback {

    private int officerID;
    private int feedbackID;

    // Simulated persistent store
    private static final List<Feedback> FEEDBACK_STORE = new ArrayList<>();

    static {
        // Dummy sample data
        FEEDBACK_STORE.add(new Feedback(1, 5,
                "Amazing cake, very fresh and tasty!",
                LocalDateTime.now().minusDays(1),
                "New", null));
        FEEDBACK_STORE.add(new Feedback(2, 3,
                "Coffee was okay but could be hotter.",
                LocalDateTime.now().minusHours(5),
                "New", null));
        FEEDBACK_STORE.add(new Feedback(3, 2,
                "Croissant was stale and hard.",
                LocalDateTime.now().minusDays(2),
                "Reviewed", "Investigated batch from 2 days ago."));
    }

    // UML: + viewAllFeedback(): List<Feedback>
    public List<Feedback> viewAllFeedback() {
        // Return a copy to avoid external modification
        return new ArrayList<>(FEEDBACK_STORE);
    }

    // UML: + viewFeedbackDetails(feedbackID): Feedback
    public Feedback viewFeedbackDetails(int feedbackID) {
        for (Feedback f : FEEDBACK_STORE) {
            if (f.getId() == feedbackID) {
                return f;
            }
        }
        return null;
    }

    // UML: + markAsReviewed(feedbackID, notes): boolean
    public boolean markAsReviewed(int feedbackID, String notes) {
        Feedback f = viewFeedbackDetails(feedbackID);
        if (f == null) {
            return false;
        }

        f.setStatus("Reviewed");
        f.setInternalNotes(notes == null ? "" : notes.trim());

        // In a real app: update DB here
        // e.g., feedbackDAO.update(f);
        return true;
    }

    // Getters & setters

    public int getOfficerID() {
        return officerID;
    }

    public void setOfficerID(int officerID) {
        this.officerID = officerID;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    // Inner Feedback class (simple DTO / entity)
    public static class Feedback {
        private int id;
        private int rating;
        private String comment;
        private LocalDateTime timestamp;
        private String status;        // "New" / "Reviewed"
        private String internalNotes; // officer notes

        public Feedback(int id, int rating, String comment,
                        LocalDateTime timestamp, String status, String internalNotes) {
            this.id = id;
            this.rating = rating;
            this.comment = comment;
            this.timestamp = timestamp;
            this.status = status;
            this.internalNotes = internalNotes;
        }

        public int getId() {
            return id;
        }

        public int getRating() {
            return rating;
        }

        public String getComment() {
            return comment;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getStatus() {
            return status;
        }

        public String getInternalNotes() {
            return internalNotes;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setInternalNotes(String internalNotes) {
            this.internalNotes = internalNotes;
        }

        // Helper for table: short comment preview
        public String getShortComment() {
            if (comment == null) return "";
            final int max = 30;
            return comment.length() <= max ? comment : comment.substring(0, max) + "...";
        }

        // Helper for table: human-friendly timestamp string
        public String getTimestampString() {
            if (timestamp == null) return "";
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return timestamp.format(fmt);
        }
    }
}
