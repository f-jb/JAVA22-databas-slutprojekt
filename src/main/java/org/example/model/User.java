package org.example.model;

public class User {
    private final String personalNumber;
    private final byte[] hash;
    private final int id;
    private final String created;
    private final String latestActivity;
    private final boolean enabled;
    private final String name;
    private final byte[] password;
    private final String email;
    private final String phone;
    private final String address;

    /*
    public void setName(String name){
        this.name = name;
    }
    void setPassword(String password){
        this.password = password;
    }
    void setEmail(String email){ this.email = email;}
    void setPhone(String phone){
        this.phone = phone;
    }
    void setAddress(String address){ this.address = address;}

     */


    User(UserBuilder builder) {
        this.name = builder.name;
        this.personalNumber = builder.personalNumber;
        this.password = builder.password;
        this.hash = builder.hash;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.id = builder.id;
        this.enabled = builder.enabled;
        this.created = builder.created;
        this.latestActivity = builder.latestActivity;
    }

    @Override
    public String toString() {
        return enabled + "\t" + id + "\t" + created + "\t" + latestActivity + "\t" + personalNumber + "\t" + password + "\t" + name + "\t" + email + "\t" + phone + "\t" + address;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }


    public String getPersonalNumber() {
        return personalNumber;
    }


    public byte[] getPassword() {
        return password;
    }


    public byte[] getHash() {
        return hash;
    }


    public String getEmail() {
        return email;
    }


    public String getPhone() {
        return phone;
    }


    public String getAddress() {
        return address;
    }

    public static class UserBuilder {
        private String name;
        private String personalNumber;
        private byte[] password;
        private byte[] hash;
        private String email;
        private String phone;
        private String address;
        private int id;
        private String created = "";
        private String latestActivity = "";
        private boolean enabled;

        public UserBuilder() {
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder personalNumber(String personalNumber) {
            this.personalNumber = personalNumber;
            return this;
        }

        public UserBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = Authentication.stringToByteArray(password);
            return this;
        }

        public UserBuilder hash(byte[] hash) {
            this.hash = hash;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder latestActivity(String latestActivity) {
            this.latestActivity = latestActivity;
            return this;
        }

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder created(String created) {
            this.created = created;
            return this;
        }

        public User build() {
            User user = new User(this);
            validateUserObject(user);
            return user;
        }

        private void validateUserObject(User user) {
            // do some sanity checks.
        }


        public UserBuilder password(byte[] passwords) {
            this.password = passwords;
            return this;
        }
    }
}
