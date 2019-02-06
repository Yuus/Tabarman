package kg.t_media.tabarman.utilites;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationData {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("surname")
        @Expose
        private String surname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("patronymic")
        @Expose
        private String patronymic;
        @SerializedName("login")
        @Expose
        private String login;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("countryId")
        @Expose
        private Integer countryId;
        @SerializedName("languageId")
        @Expose
        private Integer languageId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getLanguageId() {
            return languageId;
        }

        public void setLanguageId(Integer languageId) {
            this.languageId = languageId;
        }

}
