// import static org.mockito.Mockito.*;
// import android.content.Context;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.TextView;
// import androidx.test.core.app.ApplicationProvider;
// import com.example.myapplication.model.FoodBank;
// import org.junit.Before;
// import org.junit.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.Assert.*;

// public class FoodBankAdapterNewTest {

//     @Mock
//     private List<FoodBank> mockFoodBankList;
//     private FoodBankAdapterNew adapter;
//     private Context context = ApplicationProvider.getApplicationContext();

//     @Before
//     public void setUp() {
//         MockitoAnnotations.initMocks(this);
//         when(mockFoodBankList.size()).thenReturn(1);
//         FoodBank mockFoodBank = new FoodBank("JERSEY1", true, "JERSEY STREET", 200.0);
//         when(mockFoodBankList.get(0)).thenReturn(mockFoodBank);
//         adapter = new FoodBankAdapterNew(context, mockFoodBankList);
//     }

//     @Test
//     public void testGetView() {
//         LayoutInflater inflater = LayoutInflater.from(context);
//         ViewGroup parent = mock(ViewGroup.class);
//         when(parent.getContext()).thenReturn(context);
//         View view = inflater.inflate(R.layout.foodbankinfo_list, parent, false);
//         View resultView = adapter.getView(0, view, parent);
//         assertNotNull(resultView);
//         TextView tvName = resultView.findViewById(R.id.tv_name);
//         TextView tvStatus = resultView.findViewById(R.id.tv_status);
//         TextView tvStreet = resultView.findViewById(R.id.tv_street);
//         TextView tvDistance = resultView.findViewById(R.id.tv_distance);
//         assertEquals("JERSEY1", tvName.getText().toString());
//         assertEquals("open", tvStatus.getText().toString());
//         assertEquals("#32CD32", String.format("#%06X", (0xFFFFFF & tvStatus.getCurrentTextColor())));
//         assertEquals("JERSEY STREET", tvStreet.getText().toString());
//         assertEquals("200.0m", tvDistance.getText().toString());
//     }
// }
