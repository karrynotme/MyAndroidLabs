package algonquin.cst2335.zhou0223;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**This page represents the first page loaded
 * @author Min
 * @version 1.0
 */
@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="message")
    public String message;

    @ColumnInfo(name="TimeSent")
    public String timeSent;

    public int isSentButton;

    public ChatMessage() {
        this.id = id;
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    public ChatMessage(String message, String timeSent, boolean isSentButton) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton ? 1 : 0;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setIsSentButton(int isSentButton) {
        this.isSentButton = isSentButton;
    }

    public int isSentButton() {
        return isSentButton;
    }
}