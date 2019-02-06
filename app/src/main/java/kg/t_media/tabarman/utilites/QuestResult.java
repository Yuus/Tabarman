package kg.t_media.tabarman.utilites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestResult {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Data> data = new ArrayList<>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
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
        private String startDateTime;
        @SerializedName("endDateTime")
        @Expose
        private String endDateTime;
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

        public String getQuestCategoryId() {
            return questCategoryId;
        }

        public void setQuestCategoryId(String questCategoryId) {
            this.questCategoryId = questCategoryId;
        }

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public String getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(String endDateTime) {
            this.endDateTime = endDateTime;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }
    }

}
