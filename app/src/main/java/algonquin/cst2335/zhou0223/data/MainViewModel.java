package algonquin.cst2335.zhou0223.data;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

    public class MainViewModel extends ViewModel {
        public MutableLiveData<String> editText = new MutableLiveData<>();
        public MutableLiveData<Boolean> isSelected = new MutableLiveData<>();
    }
