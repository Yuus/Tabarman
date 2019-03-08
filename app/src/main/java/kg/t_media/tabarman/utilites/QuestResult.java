package kg.t_media.tabarman.utilites;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestResult {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("questData")
    @Expose
    private ArrayList<DataQ> dataq = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataQ> getDataq() {
        return dataq;
    }

    public void setDataq(ArrayList<DataQ> dataq) {
        this.dataq = dataq;
    }


    public class DataQ {

        @SerializedName("questId")
        @Expose
        private String questId;

        @SerializedName("questName")
        @Expose
        private String questName;

        @SerializedName("questCategoryId")
        @Expose
        private String questCategoryId;

        @SerializedName("startDateTime")
        @Expose
        private Integer startDateTime;

        @SerializedName("endDateTime")
        @Expose
        private Integer endDateTime;

        @SerializedName("countryId")
        @Expose
        private String countryId;

        public String getQuestId() {
            return questId;
        }

        public void setQuestId(String questId) {
            this.questId = questId;
        }

        public String getQuestName() {
            return questName;
        }

        public void setQuestName(String questName) {
            this.questName = questName;
        }

        public String getQuestCategoryId() { return questCategoryId; }

        public void setQuestCategoryId(String questCategoryId) { this.questCategoryId = questCategoryId; }

        public Integer getStartDateTime() { return startDateTime; }

        public void setStartDateTime(Integer startDateTime) { this.startDateTime = startDateTime; }

        public Integer getEndDateTime() { return endDateTime; }

        public void setEndDateTime(Integer endDateTime) { this.endDateTime = endDateTime; }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

    }

}
