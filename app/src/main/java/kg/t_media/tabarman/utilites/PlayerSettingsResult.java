package kg.t_media.tabarman.utilites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayerSettingsResult {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("playerId")
        @Expose
        private String playerId;
        @SerializedName("surname")
        @Expose
        private String surname;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("patronymic")
        @Expose
        private String patronymic;
        @SerializedName("languageId")
        @Expose
        private String languageId;
        @SerializedName("countryId")
        @Expose
        private String countryId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("login")
        @Expose
        private String login;

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getLanguageId() {
            return languageId;
        }

        public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

    }

}
