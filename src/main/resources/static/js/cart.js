$(document).ready(function () {
  let isUpdating = false;

  $(".quantity").change(function () {
    // Prevent multiple simultaneous requests
    if (isUpdating) {
      alert("Please wait for the previous update to complete");
      return;
    }

    let quantity = $(this).val();
    let id = $(this).attr("data-id");
    let $btn = $(this);

    // Validate quantity
    if (isNaN(quantity) || parseInt(quantity) < 1) {
      alert("Quantity must be a positive number");
      $btn.val(1);
      return;
    }

    isUpdating = true;
    $btn.prop("disabled", true);

    $.ajax({
      url: "/cart/updateCart/" + id + "/" + quantity,
      type: "GET",
      timeout: 5000,
      success: function () {
        location.reload();
      },
      error: function (xhr, status, error) {
        isUpdating = false;
        $btn.prop("disabled", false);

        if (status === "timeout") {
          alert("Request timeout. Please try again.");
        } else if (xhr.status === 0) {
          alert("Network error. Please check your connection.");
        } else {
          alert("Error updating cart. Please try again.");
        }
        console.error("Cart update error:", error);
      },
    });
  });
});
